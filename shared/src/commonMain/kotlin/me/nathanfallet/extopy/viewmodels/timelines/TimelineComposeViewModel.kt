package me.nathanfallet.extopy.viewmodels.timelines

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import me.nathanfallet.extopy.models.posts.PostPayload
import me.nathanfallet.extopy.usecases.posts.ICreatePostUseCase

class TimelineComposeViewModel(
    body: String,
    val repliedToId: String?,
    val repostOfId: String?,
    private val createPostUseCase: ICreatePostUseCase,
) : KMMViewModel() {

    // Properties

    private val _body = MutableStateFlow(viewModelScope, body)

    @NativeCoroutinesState
    val body = _body.asStateFlow()

    // Setters

    fun updateBody(value: String) {
        _body.value = value
    }

    // Methods

    @NativeCoroutines
    suspend fun send() {
        createPostUseCase(
            PostPayload(
                body = body.value,
                repliedToId = repliedToId,
                repostOfId = repostOfId,
            )
        )
    }

}
