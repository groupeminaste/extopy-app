package com.extopy.viewmodels.timelines

import com.extopy.models.posts.Post
import com.extopy.models.timelines.Timeline
import com.extopy.models.users.User
import com.extopy.usecases.posts.IUpdateLikeInPostUseCase
import com.extopy.usecases.timelines.IFetchTimelinePostsUseCase
import com.extopy.usecases.timelines.IFetchTimelineUseCase
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

class TimelineViewModel(
    private val id: UUID,
    private val fetchTimelineUseCase: IFetchTimelineUseCase,
    private val fetchTimelinePostsUseCase: IFetchTimelinePostsUseCase,
    private val updateLikeInPostUseCase: IUpdateLikeInPostUseCase,
    private val updateFollowInUserUseCase: IUpdateFollowInUserUseCase,
) : ViewModel() {

    // Properties

    private val _timeline = MutableStateFlow<Timeline?>(viewModelScope, null)
    private val _users = MutableStateFlow<List<User>?>(viewModelScope, null)
    private val _posts = MutableStateFlow<List<Post>?>(viewModelScope, null)

    @NativeCoroutinesState
    val timeline = _timeline.asStateFlow()

    @NativeCoroutinesState
    val users = _users.asStateFlow()

    @NativeCoroutinesState
    val posts = _posts.asStateFlow()

    private var hasMore = true

    // Methods

    @NativeCoroutines
    suspend fun fetchTimeline() {
        _timeline.value = fetchTimelineUseCase(id)
        fetchPosts(true)
    }

    @NativeCoroutines
    suspend fun fetchPosts(reset: Boolean = false) {
        _posts.value = if (reset) fetchTimelinePostsUseCase(id, Pagination(25, 0)).also {
            hasMore = it.isNotEmpty()
        } else (_posts.value ?: emptyList()) + fetchTimelinePostsUseCase(
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
    suspend fun onFollowClicked(user: User) {
        updateFollowInUserUseCase(user)?.let {
            _users.value = users.value?.toMutableList()?.apply {
                set(indexOf(user), it)
            }
        }
    }

    fun loadMoreIfNeeded(postId: UUID) {
        if (!hasMore || posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.coroutineScope.launch {
            fetchPosts()
        }
    }

}
