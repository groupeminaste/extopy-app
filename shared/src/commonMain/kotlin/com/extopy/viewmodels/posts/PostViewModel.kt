package com.extopy.viewmodels.posts

import com.extopy.models.posts.Post
import com.extopy.usecases.posts.IFetchPostRepliesUseCase
import com.extopy.usecases.posts.IFetchPostUseCase
import com.extopy.usecases.posts.IUpdateLikeInPostUseCase
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import dev.kaccelero.models.UUID
import dev.kaccelero.repositories.Pagination
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val id: UUID,
    private val fetchPostUseCase: IFetchPostUseCase,
    private val fetchPostRepliesUseCase: IFetchPostRepliesUseCase,
    private val updateLikeInPostUseCase: IUpdateLikeInPostUseCase,
) : ViewModel() {

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
        _posts.value = if (reset) fetchPostRepliesUseCase(id, Pagination(25, 0)).also {
            hasMore = it.isNotEmpty()
        } else (_posts.value ?: emptyList()) + fetchPostRepliesUseCase(
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

    fun loadMoreIfNeeded(postId: UUID) {
        if (!hasMore || posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.coroutineScope.launch {
            fetchReplies()
        }
    }

}
