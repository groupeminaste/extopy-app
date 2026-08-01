package com.extopy.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Fill
import com.adamglin.phosphoricons.Regular
import com.extopy.resources.Res
import com.extopy.resources.direct_message_tab
import com.extopy.resources.notifications_title
import com.extopy.resources.settings_title
import com.extopy.resources.timeline_title
import org.jetbrains.compose.resources.StringResource
import com.adamglin.phosphoricons.fill.Bell as BellFill
import com.adamglin.phosphoricons.fill.ChatCircle as ChatCircleFill
import com.adamglin.phosphoricons.fill.Gear as GearFill
import com.adamglin.phosphoricons.fill.House as HouseFill
import com.adamglin.phosphoricons.regular.Bell as BellRegular
import com.adamglin.phosphoricons.regular.ChatCircle as ChatCircleRegular
import com.adamglin.phosphoricons.regular.Gear as GearRegular
import com.adamglin.phosphoricons.regular.House as HouseRegular

/**
 * The tabs of the bottom bar.
 *
 * Each carries both weights of its icon: the outline for a tab at rest, the filled one for the tab
 * you are on.
 *
 * The labels are the screen titles, except for direct messages: "Direct Message" is the title of
 * the screen but too long for a quarter of the bar, so the tab says "Messages" instead.
 */
enum class NavigationItem(
    val route: Route,
    val icon: ImageVector,
    val iconSelected: ImageVector,
    val title: StringResource,
) {

    TIMELINE(
        Route.Timelines,
        PhosphorIcons.Regular.HouseRegular,
        PhosphorIcons.Fill.HouseFill,
        Res.string.timeline_title,
    ),
    DIRECT_MESSAGE(
        Route.DirectMessages,
        PhosphorIcons.Regular.ChatCircleRegular,
        PhosphorIcons.Fill.ChatCircleFill,
        Res.string.direct_message_tab,
    ),
    NOTIFICATIONS(
        Route.Notifications,
        PhosphorIcons.Regular.BellRegular,
        PhosphorIcons.Fill.BellFill,
        Res.string.notifications_title,
    ),
    SETTINGS(
        Route.Settings,
        PhosphorIcons.Regular.GearRegular,
        PhosphorIcons.Fill.GearFill,
        Res.string.settings_title,
    ),

}
