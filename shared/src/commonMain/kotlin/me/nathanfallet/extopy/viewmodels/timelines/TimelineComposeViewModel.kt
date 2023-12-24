package me.nathanfallet.extopy.viewmodels.timelines

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow

class TimelineComposeViewModel(
    body: String,
    val repliedToId: String?,
    val repostOfId: String?,
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

    suspend fun send() {
        /*
        sendPost(
            PostUpload(
                body = body.value ?: "",
                repliedToId = repliedToId.value,
                repostOfId = repostOfId.value
            )
        )
        */
    }

}
