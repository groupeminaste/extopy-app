package com.extopy.viewmodels.users

import com.extopy.models.posts.Post
import com.extopy.models.users.User
import com.extopy.usecases.posts.IUpdateLikeInPostUseCase
import com.extopy.usecases.users.IFetchUserPostsUseCase
import com.extopy.usecases.users.IFetchUserUseCase
import com.extopy.usecases.users.IUpdateFollowInUserUseCase
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import dev.kaccelero.models.UUID
import dev.kaccelero.repositories.Pagination
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val id: UUID,
    private val fetchUserUseCase: IFetchUserUseCase,
    private val fetchUserPostsUseCase: IFetchUserPostsUseCase,
    private val updateLikeInPostUseCase: IUpdateLikeInPostUseCase,
    private val updateFollowInUserUseCase: IUpdateFollowInUserUseCase,
) : ViewModel() {

    // Properties

    private val _user = MutableStateFlow<User?>(viewModelScope, null)
    private val _posts = MutableStateFlow<List<Post>?>(viewModelScope, null)

    @NativeCoroutinesState
    val user = _user.asStateFlow()

    @NativeCoroutinesState
    val posts = _posts.asStateFlow()

    private var hasMore = true

    // Methods

    @NativeCoroutines
    suspend fun fetchUser() {
        _user.value = fetchUserUseCase(id)
        fetchPosts(true)
    }

    @NativeCoroutines
    suspend fun fetchPosts(reset: Boolean = false) {
        _posts.value = if (reset) fetchUserPostsUseCase(id, Pagination(25, 0)).also {
            hasMore = it.isNotEmpty()
        } else (_posts.value ?: emptyList()) + fetchUserPostsUseCase(
            id, Pagination(25, posts.value?.size?.toLong() ?: 0)
        ).also {
            hasMore = it.isNotEmpty()
        }
    }

    @NativeCoroutines
    suspend fun onLikeClicked(post: Post) {
        updateLikeInPostUseCase(post)?.let {
            _posts.value = posts.value?.toMutableList()?.apply {
                set(indexOf(post), it)
            }
        }
    }

    @NativeCoroutines
    suspend fun onFollowClicked() {
        val user = user.value ?: return
        updateFollowInUserUseCase(user)?.let {
            _user.value = it
        }
    }

    fun loadMoreIfNeeded(postId: UUID) {
        if (!hasMore || posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.coroutineScope.launch {
            fetchPosts()
        }
    }

}
