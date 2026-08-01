package com.extopy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extopy.domain.models.posts.Post
import com.extopy.domain.models.users.User
import com.extopy.domain.repositories.PostsRepository
import com.extopy.domain.repositories.UsersRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

/** Searches users and posts at once, as the user types. */
@OptIn(FlowPreview::class)
class SearchViewModel(
    private val usersRepository: UsersRepository,
    private val postsRepository: PostsRepository,
) : ViewModel() {

    // Properties

    private val _search = MutableStateFlow("")

    private val _users = MutableStateFlow<List<User>?>(null)
    private val _posts = MutableStateFlow<List<Post>?>(null)

    private val _hasMoreUsers = MutableStateFlow(true)
    private val _hasMorePosts = MutableStateFlow(true)

    val search = _search.asStateFlow()

    val users = _users.asStateFlow()

    val posts = _posts.asStateFlow()

    val hasMoreUsers = _hasMoreUsers.asStateFlow()

    val hasMorePosts = _hasMorePosts.asStateFlow()

    // Setters

    fun updateSearch(value: String) {
        _search.value = value
    }

    // Methods

    init {
        viewModelScope.launch {
            // Half a second after the last keystroke: long enough that typing a word is one query
            // rather than six.
            _search.debounce(500L).collect {
                fetchUsers(true)
                fetchPosts(true)
            }
        }
    }

    suspend fun fetchUsers(reset: Boolean = false) {
        val search = currentSearch() ?: run {
            _users.value = null
            return
        }
        val offset = if (reset) 0 else _users.value?.size?.toLong() ?: 0
        val page = usersRepository.list(PAGE_SIZE, offset, search)
            .also { _hasMoreUsers.value = it.isNotEmpty() }
        _users.value = if (reset) page else (_users.value ?: emptyList()) + page
    }

    fun loadMoreUsers() {
        if (!_hasMoreUsers.value) return
        viewModelScope.launch {
            fetchUsers()
        }
    }

    suspend fun fetchPosts(reset: Boolean = false) {
        val search = currentSearch() ?: run {
            _posts.value = null
            return
        }
        val offset = if (reset) 0 else _posts.value?.size?.toLong() ?: 0
        val page = postsRepository.list(PAGE_SIZE, offset, search)
            .also { _hasMorePosts.value = it.isNotEmpty() }
        _posts.value = if (reset) page else (_posts.value ?: emptyList()) + page
    }

    fun loadMorePosts() {
        if (!_hasMorePosts.value) return
        viewModelScope.launch {
            fetchPosts()
        }
    }

    /** The query to send, or null when there is nothing worth searching for. */
    private fun currentSearch(): String? = _search.value.trim().takeIf { it.isNotBlank() }

}
