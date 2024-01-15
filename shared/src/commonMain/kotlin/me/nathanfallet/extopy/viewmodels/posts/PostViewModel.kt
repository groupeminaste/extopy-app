package me.nathanfallet.extopy.viewmodels.posts

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.usecases.posts.IFetchPostRepliesUseCase
import me.nathanfallet.extopy.usecases.posts.IFetchPostUseCase
import me.nathanfallet.extopy.usecases.posts.IUpdateLikeInPostUseCase

class PostViewModel(
    private val id: String,
    private val fetchPostUseCase: IFetchPostUseCase,
    private val fetchPostRepliesUseCase: IFetchPostRepliesUseCase,
    private val updateLikeInPostUseCase: IUpdateLikeInPostUseCase,
) : KMMViewModel() {

    // Properties

    private val _post = MutableStateFlow<Post?>(viewModelScope, null)
    private val _posts = MutableStateFlow<List<Post>?>(viewModelScope, null)

    @NativeCoroutinesState
    val post = _post.asStateFlow()

    @NativeCoroutinesState
    val posts = _posts.asStateFlow()

    private var hasMore = true

    // Methods

    @NativeCoroutines
    suspend fun fetchPost() {
        _post.value = fetchPostUseCase(id)
        fetchReplies(true)
    }

    @NativeCoroutines
    suspend fun fetchReplies(reset: Boolean = false) {
        _posts.value = if (reset) fetchPostRepliesUseCase(id, 25, 0).also {
            hasMore = it.isNotEmpty()
        } else (_posts.value ?: emptyList()) + fetchPostRepliesUseCase(
            id, 25, posts.value?.size?.toLong() ?: 0
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

    fun loadMoreIfNeeded(postId: String) {
        if (!hasMore || posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.coroutineScope.launch {
            fetchReplies()
        }
    }

}
