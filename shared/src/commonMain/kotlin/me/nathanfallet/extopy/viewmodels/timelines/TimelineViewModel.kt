package me.nathanfallet.extopy.viewmodels.timelines

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.models.timelines.Timeline
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.usecases.posts.IUpdateLikeInPostUseCase
import me.nathanfallet.extopy.usecases.timelines.IFetchTimelinePostsUseCase
import me.nathanfallet.extopy.usecases.timelines.IFetchTimelineUseCase
import me.nathanfallet.extopy.usecases.users.IUpdateFollowInUserUseCase

class TimelineViewModel(
    private val id: String,
    private val fetchTimelineUseCase: IFetchTimelineUseCase,
    private val fetchTimelinePostsUseCase: IFetchTimelinePostsUseCase,
    private val updateLikeInPostUseCase: IUpdateLikeInPostUseCase,
    private val updateFollowInUserUseCase: IUpdateFollowInUserUseCase,
) : KMMViewModel() {

    // Properties

    private val _timeline = MutableStateFlow<Timeline?>(viewModelScope, null)
    private val _users = MutableStateFlow<List<User>?>(viewModelScope, null)
    private val _posts = MutableStateFlow<List<Post>?>(viewModelScope, null)
    private val _search = MutableStateFlow<String?>(viewModelScope, null)

    @NativeCoroutinesState
    val timeline = _timeline.asStateFlow()

    @NativeCoroutinesState
    val users = _users.asStateFlow()

    @NativeCoroutinesState
    val posts = _posts.asStateFlow()

    @NativeCoroutinesState
    val search = _search.asStateFlow()

    private var hasMore = true

    // Methods

    @NativeCoroutines
    suspend fun fetchTimeline() {
        _timeline.value = fetchTimelineUseCase(id)
        fetchPosts(true)
    }

    @NativeCoroutines
    suspend fun fetchPosts(reset: Boolean = false) {
        _posts.value = if (reset) fetchTimelinePostsUseCase(id, 25, 0).also {
            hasMore = it.isNotEmpty()
        } else (_posts.value ?: emptyList()) + fetchTimelinePostsUseCase(
            id, 25, posts.value?.size?.toLong() ?: 0
        ).also {
            hasMore = it.isNotEmpty()
        }
    }

    fun updateSearch(search: String?) {
        _search.value = search
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

    @NativeCoroutines
    suspend fun doSearch() {

    }

    fun loadMoreIfNeeded(postId: String) {
        if (!hasMore || posts.value?.lastOrNull()?.id != postId) return
        viewModelScope.coroutineScope.launch {
            fetchPosts()
        }
    }

}
