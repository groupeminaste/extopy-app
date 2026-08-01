package com.extopy.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Every destination the app can navigate to.
 *
 * Ids travel as `String` rather than as `Uuid`: type-safe navigation needs a `NavType` for whatever
 * a route carries, and a text id needs none. `"null"` stands in for an absent optional id, since a
 * route argument is either present or it is not — it cannot be null.
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
