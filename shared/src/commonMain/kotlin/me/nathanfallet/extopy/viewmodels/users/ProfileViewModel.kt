package me.nathanfallet.extopy.viewmodels.users

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.usecases.posts.IFetchUserPostsUseCase
import me.nathanfallet.extopy.usecases.posts.IUpdateLikeInPostUseCase
import me.nathanfallet.extopy.usecases.users.IFetchUserUseCase
import me.nathanfallet.extopy.usecases.users.IUpdateFollowInUserUseCase

class ProfileViewModel(
    private val id: String,
    private val fetchUserUseCase: IFetchUserUseCase,
    private val fetchUserPostsUseCase: IFetchUserPostsUseCase,
    private val updateLikeInPostUseCase: IUpdateLikeInPostUseCase,
    private val updateFollowInUserUseCase: IUpdateFollowInUserUseCase,
) : KMMViewModel() {

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
        if (reset) {
            _posts.value = fetchUserPostsUseCase(id, 0, 25).also {
                hasMore = it.isNotEmpty()
            }
        } else {
            _posts.value = (_posts.value ?: emptyList()) + fetchUserPostsUseCase(
                id, posts.value?.size?.toLong() ?: 0, 25
            ).also {
                hasMore = it.isNotEmpty()
            }
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

    fun loadMoreIfNeeded(postId: String) {
        if (!hasMore || posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.coroutineScope.launch {
            fetchPosts()
        }
    }

}
