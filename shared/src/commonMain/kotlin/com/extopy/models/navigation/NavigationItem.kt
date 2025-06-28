package com.extopy.models.navigation

import extopy_app.shared.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class NavigationItem(
    val route: Route,
    val icon: DrawableResource,
    val title: StringResource,
) {

    TIMELINE(
        Route.Timelines,
        Res.drawable.ic_baseline_menu_24,
        Res.string.timeline_title
    ),
    DIRECT_MESSAGE(
        Route.DirectMessages,
        Res.drawable.ic_baseline_message_24,
        Res.string.direct_message_title
    ),
    NOTIFICATIONS(
        Route.Notifications,
        Res.drawable.ic_baseline_notifications_24,
        Res.string.notifications_title
    ),
    SETTINGS(
        Route.Settings,
        Res.drawable.ic_baseline_settings_24,
        Res.string.settings_title
    )

}
