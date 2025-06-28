package com.extopy.viewmodels.timelines

import androidx.lifecycle.ViewModel
import com.extopy.models.posts.PostPayload
import com.extopy.usecases.posts.ICreatePostUseCase
import dev.kaccelero.models.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimelineComposeViewModel(
    body: String,
    val repliedToId: UUID?,
    val repostOfId: UUID?,
    private val createPostUseCase: ICreatePostUseCase,
) : ViewModel() {

    // Properties

    private val _body = MutableStateFlow(body)

    val body = _body.asStateFlow()

    // Setters

    fun updateBody(value: String) {
        _body.value = value
    }

    // Methods

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
