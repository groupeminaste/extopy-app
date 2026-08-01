package com.extopy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.extopy.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.uuid.Uuid

/**
 * Composing a post.
 *
 * The same screen writes a new post, a reply and a repost — which of the three it is comes from
 * [repliedToId] and [repostOfId] being set or not.
 */
class TimelineComposeViewModel(
    body: String,
    val repliedToId: Uuid?,
    val repostOfId: Uuid?,
    private val postsRepository: PostsRepository,
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
        postsRepository.create(
            body = _body.value,
            repliedToId = repliedToId,
            repostOfId = repostOfId,
        )
    }

}
