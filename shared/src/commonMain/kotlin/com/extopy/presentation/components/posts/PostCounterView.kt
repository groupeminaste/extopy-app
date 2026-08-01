package com.extopy.presentation.components.posts

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.extopy.presentation.theme.CounterStyle
import com.extopy.presentation.theme.ExtopyTheme
import com.extopy.presentation.theme.PillShape
import com.extopy.presentation.util.simplify
import androidx.compose.ui.graphics.vector.ImageVector
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Fill
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.fill.ChatCircle as ChatCircleFill
import com.adamglin.phosphoricons.fill.Heart as HeartFill
import com.adamglin.phosphoricons.fill.Repeat as RepeatFill
import com.adamglin.phosphoricons.regular.ChatCircle as ChatCircleRegular
import com.adamglin.phosphoricons.regular.Heart as HeartRegular
import com.adamglin.phosphoricons.regular.Repeat as RepeatRegular

/**
 * One counter of a post, as a bubble.
 *
 * The bubble is what makes these read as actions rather than as a caption: it gives the tap a
 * visible target, and lets an active state be shown by the ground rather than only by the icon.
 */
@Composable
fun PostCounterView(
    type: PostCounter,
    value: Long,
    active: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val semantic = ExtopyTheme.semantic
    val content by animateColorAsState(
        activeColor(type, active) ?: MaterialTheme.colorScheme.onSurfaceVariant
    )
    val background by animateColorAsState(
        if (active) semantic.bubbleActive else semantic.bubble
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(PillShape)
            .background(background)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp),
    ) {
        Icon(
            imageVector = imageForCounter(type, active),
            contentDescription = type.name,
            tint = content,
            modifier = Modifier.size(15.dp),
        )
        Text(
            text = value.simplify(),
            style = CounterStyle,
            color = content,
        )
    }
}

fun imageForCounter(type: PostCounter, active: Boolean): ImageVector = when (type) {
    PostCounter.LIKES -> if (active) PhosphorIcons.Fill.HeartFill else PhosphorIcons.Regular.HeartRegular
    PostCounter.REPOSTS -> if (active) PhosphorIcons.Fill.RepeatFill else PhosphorIcons.Regular.RepeatRegular
    PostCounter.REPLIES -> if (active) PhosphorIcons.Fill.ChatCircleFill else PhosphorIcons.Regular.ChatCircleRegular
}

/** The colour an active counter takes; null when the action has no state of its own. */
@Composable
private fun activeColor(type: PostCounter, active: Boolean): Color? {
    if (!active) return null
    val semantic = ExtopyTheme.semantic
    return when (type) {
        PostCounter.LIKES -> semantic.liked
        PostCounter.REPOSTS -> semantic.reposted
        PostCounter.REPLIES -> null
    }
}
