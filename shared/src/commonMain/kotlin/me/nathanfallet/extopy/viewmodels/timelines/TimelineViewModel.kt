package me.nathanfallet.extopy.viewmodels.timelines

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.models.timelines.Timeline
import me.nathanfallet.extopy.models.users.User
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
    suspend fun onLikeClicked(post: Post) {
        updateLikeInPostUseCase(post)?.let {
            _timeline.value = _timeline.value?.copy(
                posts = _timeline.value?.posts?.toMutableList()?.apply {
                    set(indexOf(post), it)
                }
            )
        }
    }

    @NativeCoroutines
    suspend fun onFollowClicked(user: User) {
        updateFollowInUserUseCase(user)?.let {
            _timeline.value = _timeline.value?.copy(
                users = _timeline.value?.users?.toMutableList()?.apply {
                    set(indexOf(user), it)
                }
            )
        }
    }

    @NativeCoroutines
    suspend fun doSearch() {

    }

}
