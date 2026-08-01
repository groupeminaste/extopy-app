package com.extopy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extopy.domain.models.posts.Post
import com.extopy.domain.repositories.PostsRepository
import com.extopy.domain.usecases.posts.ToggleLikeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid

/** A post and the replies under it. */
class PostViewModel(
    private val id: Uuid,
    private val postsRepository: PostsRepository,
    private val toggleLike: ToggleLikeUseCase,
) : ViewModel() {

    // Properties

    private val _post = MutableStateFlow<Post?>(null)
    private val _posts = MutableStateFlow<List<Post>?>(null)

    val post = _post.asStateFlow()

    val posts = _posts.asStateFlow()

    private var hasMore = true

    // Methods

    suspend fun fetchPost() {
        _post.value = postsRepository.get(id)
        fetchReplies(true)
    }

    suspend fun fetchReplies(reset: Boolean = false) {
        val offset = if (reset) 0 else _posts.value?.size?.toLong() ?: 0
        val page = postsRepository.listReplies(id, PAGE_SIZE, offset).also { hasMore = it.isNotEmpty() }
        _posts.value = if (reset) page else (_posts.value ?: emptyList()) + page
    }

    suspend fun onLikeClicked(post: Post) {
        toggleLike(post)?.let {
            _posts.value = _posts.value?.toMutableList()?.apply {
                set(indexOf(post), it)
            }
        }
    }

    fun loadMoreIfNeeded(postId: Uuid) {
        if (!hasMore || _posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.launch {
            fetchReplies()
        }
    }

}
