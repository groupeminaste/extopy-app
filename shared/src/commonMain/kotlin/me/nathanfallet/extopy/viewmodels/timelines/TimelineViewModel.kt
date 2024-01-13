package me.nathanfallet.extopy.viewmodels.timelines

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.models.posts.PostCounter
import me.nathanfallet.extopy.models.timelines.Timeline
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.models.users.UserButton
import me.nathanfallet.extopy.models.users.UserCounter
import me.nathanfallet.extopy.usecases.posts.IUpdateLikeInPostUseCase
import me.nathanfallet.extopy.usecases.timelines.IFetchTimelineUseCase
import me.nathanfallet.extopy.usecases.users.IUpdateFollowInUserUseCase

class TimelineViewModel(
    private val id: String,
    private val fetchTimelineUseCase: IFetchTimelineUseCase,
    private val updateLikeInPostUseCase: IUpdateLikeInPostUseCase,
    private val updateFollowInUserUseCase: IUpdateFollowInUserUseCase,
) : KMMViewModel() {

    // Properties

    private val _timeline = MutableStateFlow<Timeline?>(viewModelScope, null)
    private val _search = MutableStateFlow<String?>(viewModelScope, null)

    @NativeCoroutinesState
    val timeline = _timeline.asStateFlow()

    @NativeCoroutinesState
    val search = _search.asStateFlow()

    // Methods

    @NativeCoroutines
    suspend fun fetchTimeline() {
        _timeline.value = fetchTimelineUseCase(id)
    }

    fun updateSearch(search: String?) {
        _search.value = search
    }

    @NativeCoroutines
    suspend fun counterClicked(post: Post, type: PostCounter) {
        when (type) {
            PostCounter.REPLIES -> {} //navigate?.invoke("timeline/compose?repliedToId=${post.id}")
            PostCounter.REPOSTS -> {} //navigate?.invoke("timeline/compose?repostOfId=${post.id}")
            PostCounter.LIKES -> updateLikeInPostUseCase(post)?.let {
                _timeline.value = _timeline.value?.copy(
                    posts = _timeline.value?.posts?.toMutableList()?.apply {
                        set(indexOf(post), it)
                    }
                )
            }
        }
    }

    @NativeCoroutines
    suspend fun counterClicked(user: User, type: UserCounter) {
        when (type) {
            UserCounter.FOLLOWERS -> {} //navigate?.invoke("timeline/user/${user.id}/followers")
            UserCounter.FOLLOWING -> {} //navigate?.invoke("timeline/user/${user.id}/following")
            UserCounter.POSTS -> {} //navigate?.invoke("timeline/user/${user.id}/posts")
        }
    }

    @NativeCoroutines
    suspend fun buttonClicked(user: User, type: UserButton) {
        when (type) {
            UserButton.EDIT -> {}
            UserButton.FOLLOW -> updateFollowInUserUseCase(user)?.let {
                _timeline.value = _timeline.value?.copy(
                    users = _timeline.value?.users?.toMutableList()?.apply {
                        set(indexOf(user), it)
                    }
                )
            }

            UserButton.SETTINGS -> {}
            UserButton.DC -> {}
        }
    }

    @NativeCoroutines
    suspend fun doSearch() {

    }

}
