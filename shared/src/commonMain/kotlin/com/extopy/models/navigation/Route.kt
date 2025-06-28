package com.extopy.models.navigation

import dev.kaccelero.models.UUID
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    object Auth : Route

    @Serializable
    data class AuthWithCode(
        val code: String,
    ) : Route

    @Serializable
    data object Timelines : Route

    @Serializable
    data class TimelineCompose(
        val repliedToId: UUID? = null,
        val repostOfId: UUID? = null,
    ) : Route

    @Serializable
    data class TimelineUser(
        val userId: UUID,
    ) : Route

    @Serializable
    data class TimelinePost(
        val postId: UUID,
    ) : Route

    @Serializable
    data object DirectMessages : Route

    @Serializable
    data object Notifications : Route

    @Serializable
    data object Settings : Route

}
