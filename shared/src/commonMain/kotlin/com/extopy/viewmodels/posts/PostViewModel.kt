package com.extopy.viewmodels.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extopy.models.posts.Post
import com.extopy.usecases.posts.IFetchPostRepliesUseCase
import com.extopy.usecases.posts.IFetchPostUseCase
import com.extopy.usecases.posts.IUpdateLikeInPostUseCase
import dev.kaccelero.models.UUID
import dev.kaccelero.repositories.Pagination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(
    private val id: UUID,
    private val fetchPostUseCase: IFetchPostUseCase,
    private val fetchPostRepliesUseCase: IFetchPostRepliesUseCase,
    private val updateLikeInPostUseCase: IUpdateLikeInPostUseCase,
) : ViewModel() {

    // Properties

    private val _post = MutableStateFlow<Post?>(null)
    private val _posts = MutableStateFlow<List<Post>?>(null)

    val post = _post.asStateFlow()

    val posts = _posts.asStateFlow()

    private var hasMore = true

    // Methods

    suspend fun fetchPost() {
        _post.value = fetchPostUseCase(id)
        fetchReplies(true)
    }

    suspend fun fetchReplies(reset: Boolean = false) {
        _posts.value = if (reset) fetchPostRepliesUseCase(id, Pagination(25, 0)).also {
            hasMore = it.isNotEmpty()
        } else (_posts.value ?: emptyList()) + fetchPostRepliesUseCase(
            id, Pagination(25, posts.value?.size?.toLong() ?: 0)
        ).also {
            hasMore = it.isNotEmpty()
        }
    }

    suspend fun onLikeClicked(post: Post) {
        updateLikeInPostUseCase(post)?.let {
            _posts.value = posts.value?.toMutableList()?.apply {
                set(indexOf(post), it)
            }
        }
    }

    fun loadMoreIfNeeded(postId: UUID) {
        if (!hasMore || posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.launch {
            fetchReplies()
        }
    }

}
