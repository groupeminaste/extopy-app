package me.nathanfallet.extopy.ui.components.posts

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.nathanfallet.extopy.R
import me.nathanfallet.extopy.extensions.simplify
import me.nathanfallet.extopy.models.posts.PostCounter
import me.nathanfallet.extopy.ui.theme.likedColor
import me.nathanfallet.extopy.ui.theme.repostedColor

@Composable
fun PostCounterView(
    type: PostCounter,
    value: Long,
    active: Boolean,
    onClick: (PostCounter) -> Unit,
) {

    val color = colorForCounter(type, active) ?: MaterialTheme.colorScheme.onSurface

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick(type) }
    ) {
        Image(
            painter = painterResource(id = imageForCounter(type, active)),
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

fun imageForCounter(type: PostCounter, active: Boolean): Int {
    return when (type) {
        PostCounter.LIKES -> if (active) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
        PostCounter.REPOSTS -> if (active) R.drawable.ic_baseline_repeat_on_24 else R.drawable.ic_baseline_repeat_24
        PostCounter.REPLIES -> if (active) R.drawable.ic_baseline_chat_bubble_24 else R.drawable.ic_baseline_chat_bubble_outline_24
    }
}

fun colorForCounter(type: PostCounter, active: Boolean): Color? {
    return when (type) {
        PostCounter.LIKES -> if (active) likedColor else null
        PostCounter.REPOSTS -> if (active) repostedColor else null
        else -> null
    }
}
