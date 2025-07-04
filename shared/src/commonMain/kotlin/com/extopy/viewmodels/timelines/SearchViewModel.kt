package com.extopy.viewmodels.timelines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extopy.models.application.SearchOptions
import com.extopy.models.posts.Post
import com.extopy.models.users.User
import com.extopy.usecases.posts.IFetchPostsUseCase
import com.extopy.usecases.users.IFetchUsersUseCase
import dev.kaccelero.repositories.Pagination
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val fetchUsersUseCase: IFetchUsersUseCase,
    private val fetchPostsUseCase: IFetchPostsUseCase,
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
            _search.debounce(500L).collect {
                fetchUsers(true)
                fetchPosts(true)
            }
        }
    }

    suspend fun fetchUsers(reset: Boolean = false) {
        val search = search.value.trim().takeIf { it.isNotBlank() } ?: run {
            _users.value = null
            return
        }
        _users.value = if (reset) fetchUsersUseCase(Pagination(25, 0, SearchOptions(search))).also {
            _hasMoreUsers.value = it.isNotEmpty()
        } else (_users.value ?: emptyList()) + fetchUsersUseCase(
            Pagination(25, users.value?.size?.toLong() ?: 0, SearchOptions(search))
        ).also {
            _hasMoreUsers.value = it.isNotEmpty()
        }
    }

    fun loadMoreUsers() {
        if (!hasMoreUsers.value) return
        viewModelScope.launch {
            fetchUsers()
        }
    }

    suspend fun fetchPosts(reset: Boolean = false) {
        val search = search.value.trim().takeIf { it.isNotBlank() } ?: run {
            _posts.value = null
            return
        }
        _posts.value = if (reset) fetchPostsUseCase(Pagination(25, 0, SearchOptions(search))).also {
            _hasMorePosts.value = it.isNotEmpty()
        } else (_posts.value ?: emptyList()) + fetchPostsUseCase(
            Pagination(25, _posts.value?.size?.toLong() ?: 0, SearchOptions(search))
        ).also {
            _hasMorePosts.value = it.isNotEmpty()
        }
    }

    fun loadMorePosts() {
        if (!hasMorePosts.value) return
        viewModelScope.launch {
            fetchPosts()
        }
    }


}
