package com.extopy.viewmodels.timelines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extopy.models.posts.Post
import com.extopy.models.timelines.Timeline
import com.extopy.models.users.User
import com.extopy.usecases.posts.IUpdateLikeInPostUseCase
import com.extopy.usecases.timelines.IFetchTimelinePostsUseCase
import com.extopy.usecases.timelines.IFetchTimelineUseCase
import com.extopy.usecases.users.IUpdateFollowInUserUseCase
import dev.kaccelero.models.UUID
import dev.kaccelero.repositories.Pagination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimelineViewModel(
    private val id: UUID,
    private val fetchTimelineUseCase: IFetchTimelineUseCase,
    private val fetchTimelinePostsUseCase: IFetchTimelinePostsUseCase,
    private val updateLikeInPostUseCase: IUpdateLikeInPostUseCase,
    private val updateFollowInUserUseCase: IUpdateFollowInUserUseCase,
) : ViewModel() {

    // Properties

    private val _timeline = MutableStateFlow<Timeline?>(null)
    private val _users = MutableStateFlow<List<User>?>(null)
    private val _posts = MutableStateFlow<List<Post>?>(null)

    val timeline = _timeline.asStateFlow()

    val users = _users.asStateFlow()

    val posts = _posts.asStateFlow()

    private var hasMore = true

    // Methods

    suspend fun fetchTimeline() {
        _timeline.value = fetchTimelineUseCase(id)
        fetchPosts(true)
    }

    suspend fun fetchPosts(reset: Boolean = false) {
        _posts.value = if (reset) fetchTimelinePostsUseCase(id, Pagination(25, 0)).also {
            hasMore = it.isNotEmpty()
        } else (_posts.value ?: emptyList()) + fetchTimelinePostsUseCase(
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

    suspend fun onFollowClicked(user: User) {
        updateFollowInUserUseCase(user)?.let {
            _users.value = users.value?.toMutableList()?.apply {
                set(indexOf(user), it)
            }
        }
    }

    fun loadMoreIfNeeded(postId: UUID) {
        if (!hasMore || posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.launch {
            fetchPosts()
        }
    }

}
