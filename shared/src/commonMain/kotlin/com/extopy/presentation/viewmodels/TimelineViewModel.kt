package com.extopy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extopy.domain.models.posts.Post
import com.extopy.domain.models.timelines.Timeline
import com.extopy.domain.models.users.User
import com.extopy.domain.repositories.TimelinesRepository
import com.extopy.domain.usecases.posts.ToggleLikeUseCase
import com.extopy.domain.usecases.users.ToggleFollowUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid

/** A timeline: the posts the server picked, plus the users it suggests following. */
class TimelineViewModel(
    private val id: Uuid,
    private val timelinesRepository: TimelinesRepository,
    private val toggleLike: ToggleLikeUseCase,
    private val toggleFollow: ToggleFollowUseCase,
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
        _timeline.value = timelinesRepository.get(id)
        fetchPosts(true)
    }

    suspend fun fetchPosts(reset: Boolean = false) {
        val offset = if (reset) 0 else _posts.value?.size?.toLong() ?: 0
        val page = timelinesRepository.listPosts(id, PAGE_SIZE, offset).also { hasMore = it.isNotEmpty() }
        _posts.value = if (reset) page else (_posts.value ?: emptyList()) + page
    }

    suspend fun onLikeClicked(post: Post) {
        val updated = toggleLike(post) ?: return
        _posts.value = _posts.value?.map { if (it.id == updated.id) updated else it }
    }

    suspend fun onFollowClicked(user: User) {
        val updated = toggleFollow(user) ?: return
        _users.value = _users.value?.map { if (it.id == updated.id) updated else it }
    }

    fun loadMoreIfNeeded(postId: Uuid) {
        if (!hasMore || _posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.launch {
            fetchPosts()
        }
    }

}
