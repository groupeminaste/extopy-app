package com.extopy.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Fill
import com.adamglin.phosphoricons.Regular
import com.extopy.domain.models.timelines.Timeline
import com.extopy.domain.models.users.User
import com.extopy.presentation.components.base.ExtopyNavBar
import com.extopy.presentation.components.base.NavBarItem
import com.extopy.presentation.navigation.MainTab
import com.extopy.presentation.navigation.Route
import com.extopy.resources.Res
import com.extopy.resources.direct_message_tab
import com.extopy.resources.notifications_title
import com.extopy.resources.settings_title
import com.extopy.resources.timeline_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import com.adamglin.phosphoricons.fill.Bell as BellFill
import com.adamglin.phosphoricons.fill.ChatCircle as ChatCircleFill
import com.adamglin.phosphoricons.fill.Gear as GearFill
import com.adamglin.phosphoricons.fill.House as HouseFill
import com.adamglin.phosphoricons.regular.Bell as BellRegular
import com.adamglin.phosphoricons.regular.ChatCircle as ChatCircleRegular
import com.adamglin.phosphoricons.regular.Gear as GearRegular
import com.adamglin.phosphoricons.regular.House as HouseRegular

/**
 * The label of a tab, which is its screen's title — except for direct messages: "Direct Message"
 * names the screen but does not fit a quarter of the bar, so the tab says "Messages".
 */
private val MainTab.labelRes: StringResource
    get() = when (this) {
        MainTab.TIMELINE -> Res.string.timeline_title
        MainTab.DIRECT_MESSAGE -> Res.string.direct_message_tab
        MainTab.NOTIFICATIONS -> Res.string.notifications_title
        MainTab.SETTINGS -> Res.string.settings_title
    }

/** Outline (at rest) and filled (current) icons for each tab. */
private val MainTab.icons: Pair<ImageVector, ImageVector>
    get() = when (this) {
        MainTab.TIMELINE -> PhosphorIcons.Regular.HouseRegular to PhosphorIcons.Fill.HouseFill
        MainTab.DIRECT_MESSAGE -> PhosphorIcons.Regular.ChatCircleRegular to PhosphorIcons.Fill.ChatCircleFill
        MainTab.NOTIFICATIONS -> PhosphorIcons.Regular.BellRegular to PhosphorIcons.Fill.BellFill
        MainTab.SETTINGS -> PhosphorIcons.Regular.GearRegular to PhosphorIcons.Fill.GearFill
    }

/**
 * The signed-in app.
 *
 * The current tab is state held here, not a navigation destination: switching tabs is not going
 * somewhere, and making it a navigation event is what used to run a transition across the whole
 * screen every time. It survives configuration changes through [rememberSaveable].
 *
 * The bar lives here too, which is why it is simply absent from the screens you open *out* of a
 * tab — a post, a profile, the composer.
 */
@Composable
fun MainScreen(
    viewedBy: User,
    navigate: (Route) -> Unit,
) {
    var tabIndex by rememberSaveable { mutableStateOf(0) }
    val tab = MainTab.entries[tabIndex]

    val items = MainTab.entries.map { entry ->
        val (icon, iconSelected) = entry.icons
        NavBarItem(
            key = entry.name.lowercase(),
            icon = icon,
            iconSelected = iconSelected,
            label = stringResource(entry.labelRes),
        )
    }

    Scaffold(
        bottomBar = {
            ExtopyNavBar(
                items = items,
                selectedKey = tab.name.lowercase(),
                onSelect = { key ->
                    tabIndex = MainTab.entries.first { it.name.lowercase() == key }.ordinal
                },
            )
        },
    ) { padding ->
        val content = Modifier.padding(padding)
        when (tab) {
            MainTab.TIMELINE -> TimelineScreen(
                id = Timeline.defaultId,
                viewedBy = viewedBy,
                navigate = navigate,
                modifier = content,
            )

            MainTab.DIRECT_MESSAGE -> DirectMessagesScreen(modifier = content)
            MainTab.NOTIFICATIONS -> NotificationsScreen(modifier = content)
            MainTab.SETTINGS -> SettingsScreen(modifier = content)
        }
    }
}
