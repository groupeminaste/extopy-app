package com.extopy.ui.components.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.extopy.extensions.simplify
import com.extopy.models.posts.PostCounter
import com.extopy.ui.theme.likedColor
import com.extopy.ui.theme.repostedColor
import extopy_app.shared.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun PostCounterView(
    type: PostCounter,
    value: Long,
    active: Boolean,
    onClick: () -> Unit,
) {

    val color = colorForCounter(type, active) ?: MaterialTheme.colorScheme.onSurface

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(imageForCounter(type, active)),
            contentDescription = type.name,
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier
                .size(MaterialTheme.typography.bodyMedium.fontSize.value.dp)
        )
        Text(
            text = value.simplify(),
            style = MaterialTheme.typography.bodyMedium,
            color = color
        )
    }

}

fun imageForCounter(type: PostCounter, active: Boolean): DrawableResource = when (type) {
    PostCounter.LIKES -> if (active) Res.drawable.ic_baseline_favorite_24 else Res.drawable.ic_baseline_favorite_border_24
    PostCounter.REPOSTS -> if (active) Res.drawable.ic_baseline_repeat_on_24 else Res.drawable.ic_baseline_repeat_24
    PostCounter.REPLIES -> if (active) Res.drawable.ic_baseline_chat_bubble_24 else Res.drawable.ic_baseline_chat_bubble_outline_24
}

fun colorForCounter(type: PostCounter, active: Boolean): Color? = when (type) {
    PostCounter.LIKES -> if (active) likedColor else null
    PostCounter.REPOSTS -> if (active) repostedColor else null
    else -> null
}
