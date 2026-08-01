package com.extopy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extopy.domain.models.posts.Post
import com.extopy.domain.models.users.User
import com.extopy.domain.repositories.UsersRepository
import com.extopy.domain.usecases.posts.ToggleLikeUseCase
import com.extopy.domain.usecases.users.ToggleFollowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid

/** One user's profile and the posts they published. */
class ProfileViewModel(
    private val id: Uuid,
    private val usersRepository: UsersRepository,
    private val toggleLike: ToggleLikeUseCase,
    private val toggleFollow: ToggleFollowUseCase,
) : ViewModel() {

    // Properties

    private val _user = MutableStateFlow<User?>(null)
    private val _posts = MutableStateFlow<List<Post>?>(null)

    val user = _user.asStateFlow()

    val posts = _posts.asStateFlow()

    private var hasMore = true

    // Methods

    suspend fun fetchUser() {
        _user.value = usersRepository.get(id)
        fetchPosts(true)
    }

    suspend fun fetchPosts(reset: Boolean = false) {
        val offset = if (reset) 0 else _posts.value?.size?.toLong() ?: 0
        val page = usersRepository.listPosts(id, PAGE_SIZE, offset).also { hasMore = it.isNotEmpty() }
        _posts.value = if (reset) page else (_posts.value ?: emptyList()) + page
    }

    suspend fun onLikeClicked(post: Post) {
        val updated = toggleLike(post) ?: return
        _posts.value = _posts.value?.map { if (it.id == updated.id) updated else it }
    }

    suspend fun onFollowClicked() {
        val user = _user.value ?: return
        toggleFollow(user)?.let {
            _user.value = it
        }
    }

    fun loadMoreIfNeeded(postId: Uuid) {
        if (!hasMore || _posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.launch {
            fetchPosts()
        }
    }

}
