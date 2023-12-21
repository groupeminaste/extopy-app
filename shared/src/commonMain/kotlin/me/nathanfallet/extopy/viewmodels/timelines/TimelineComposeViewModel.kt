package me.nathanfallet.extopy.viewmodels.timelines

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimelineComposeViewModel(
    body: String,
    private val repliedToId: String?,
    private val repostOfId: String?,
) : KMMViewModel() {

    // Properties

    private val _body = MutableStateFlow(viewModelScope, body)
    val body = _body.asStateFlow()

    // Setters

    fun updateBody(value: String) {
        _body.value = value
    }

    // Methods

    suspend fun send() {
        println("Send post")
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
