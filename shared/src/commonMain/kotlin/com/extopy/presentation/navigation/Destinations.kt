package com.extopy.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Every destination the app can navigate to.
 *
 * The four tabs are deliberately *not* in here: they are a state of [Route.Main], not places you
 * navigate to. Making them destinations meant every tab change ran a navigation transition, which
 * is both wrong — you are not going anywhere — and visible.
 *
 * Ids travel as `String` rather than as `Uuid`: type-safe navigation needs a `NavType` for whatever
 * a route carries, and a text id needs none. `"null"` stands in for an absent optional id, since a
 * route argument is either present or it is not — it cannot be null.
 */
sealed interface Route {

    @Serializable
    data object Auth : Route

    @Serializable
    data class AuthWithCode(
        val code: String,
    ) : Route

    /** The signed-in app: the four tabs and the bar that switches between them. */
    @Serializable
    data object Main : Route

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

}

/** The tabs inside [Route.Main]. */
enum class MainTab {
    TIMELINE,
    DIRECT_MESSAGE,
    NOTIFICATIONS,
    SETTINGS,
}
