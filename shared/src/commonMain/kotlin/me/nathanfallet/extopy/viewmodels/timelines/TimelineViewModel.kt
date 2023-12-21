package me.nathanfallet.extopy.viewmodels.timelines

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.models.timelines.Timeline
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.usecases.timelines.IFetchTimelineUseCase

class TimelineViewModel(
    private val id: String,
    private val fetchTimelineUseCase: IFetchTimelineUseCase,
) : KMMViewModel() {

    // Properties

    private val _timeline = MutableStateFlow<Timeline?>(viewModelScope, null)
    val timeline = _timeline.asStateFlow()

    private val _search = MutableStateFlow<String?>(viewModelScope, null)
    val search = _search.asStateFlow()

    // Methods

    suspend fun fetchTimeline() {
        _timeline.value = fetchTimelineUseCase(id)
    }

    fun updateSearch(search: String?) {
        _search.value = search
    }

    fun counterClicked(post: Post, id: String) {
        when (id) {
            //"replies" -> navigate?.invoke("timeline/compose?repliedToId=${post.id}")
            //"reposts" -> navigate?.invoke("timeline/compose?repostOfId=${post.id}")
            "likes" -> {
                try {
                    //post.sendLike(post.id, !post.likesIn)
                    //fetchTimeline()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun counterClicked(user: User, id: String) {

    }

    fun buttonClicked(user: User, id: Int) {
        /*
        when (id) {
            R.string.timeline_button_edit -> {}
            R.string.timeline_button_follow -> {
                viewModelScope.launch {
                    try {
                        //user.sendFollow(user.id, true)
                        loadTimelines()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            R.string.timeline_button_following -> {
                viewModelScope.launch {
                    try {
                        //user.sendFollow(user.id, false)
                        loadTimelines()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        */
    }

    fun search() {

    }

}
