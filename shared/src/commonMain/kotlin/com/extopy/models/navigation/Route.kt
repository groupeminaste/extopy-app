package com.extopy.models.navigation

import kotlinx.serialization.Serializable

/*
 * We're using `String` instead of `UUID` for the route parameters because of this error:
 *
 * kotlin.IllegalStateException: Cannot find KSerializer for [dev.kaccelero.models.UUID].
 * If applicable, custom KSerializers for custom and third-party KType is currently
 * not supported when declared directly on a class field via @Serializable(with = ...).
 * Please use @Serializable or @Serializable(with = ...) on the class or object declaration.
 */

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
        val repliedToId: String = "null",
        val repostOfId: String = "null",
    ) : Route

    @Serializable
    data class TimelineUser(
        val userId: String,
    ) : Route

    @Serializable
    data class TimelinePost(
        val postId: String,
    ) : Route

    @Serializable
    data object DirectMessages : Route

    @Serializable
    data object Notifications : Route

    @Serializable
    data object Settings : Route

}
