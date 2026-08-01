package com.extopy.presentation.components.base

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.extopy.presentation.navigation.NavigationItem
import com.extopy.presentation.theme.PillShape
import org.jetbrains.compose.resources.stringResource

/**
 * The tab bar, as a pill floating clear of the screen edge.
 *
 * Material's `NavigationBar` is a full-bleed band with its own elevation and its own insets, which
 * is what squeezed "Direct Message" onto two lines. Here the label sits under its icon with the
 * whole tab width to itself, and the current tab is a filled pill rather than a small indicator
 * behind the glyph.
 *
 * Which tab is current is decided by the caller rather than guessed from the route name, and
 * selecting one raises an event rather than navigating: this component knows nothing about
 * navigation, which is what makes it previewable and testable.
 */
@Composable
fun ExtopyNavBar(
    items: List<NavigationItem>,
    selected: NavigationItem?,
    onSelect: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            // Keeps the pill clear of the gesture bar instead of sitting underneath it.
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth()
            .clip(PillShape)
            .background(MaterialTheme.colorScheme.surface)
            .padding(6.dp),
    ) {
        items.forEach { item ->
            NavBarTab(
                item = item,
                selected = item == selected,
                onClick = { onSelect(item) },
            )
        }
    }
}

@Composable
private fun RowScope.NavBarTab(
    item: NavigationItem,
    selected: Boolean,
    onClick: () -> Unit,
) {
    // Colour and width both settle rather than cut, so switching tabs reads as one movement.
    val background by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    )
    val content by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
    )

    val label = stringResource(item.title)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier
            .weight(1f)
            .testTag("tab_${item.name.lowercase()}")
            .clip(PillShape)
            .background(background)
            .selectable(
                selected = selected,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            )
            .padding(vertical = 8.dp, horizontal = 4.dp),
    ) {
        Icon(
            imageVector = if (selected) item.iconSelected else item.icon,
            contentDescription = null,
            tint = content,
            modifier = Modifier.size(22.dp),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = content,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}
