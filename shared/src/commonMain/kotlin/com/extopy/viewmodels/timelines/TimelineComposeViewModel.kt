package com.extopy.viewmodels.timelines

import com.extopy.models.posts.PostPayload
import com.extopy.usecases.posts.ICreatePostUseCase
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import dev.kaccelero.models.UUID
import kotlinx.coroutines.flow.asStateFlow

class TimelineComposeViewModel(
    body: String,
    val repliedToId: UUID?,
    val repostOfId: UUID?,
    private val createPostUseCase: ICreatePostUseCase,
) : ViewModel() {

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
