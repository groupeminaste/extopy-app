package me.nathanfallet.extopy.extensions

import androidx.compose.ui.graphics.Color
import me.nathanfallet.extopy.R
import me.nathanfallet.extopy.ui.theme.likedColor
import me.nathanfallet.extopy.ui.theme.repostedColor

fun String.counterToString(): Int {
    return when (this) {
        "posts" -> R.string.timeline_info_posts
        "followers" -> R.string.timeline_info_followers
        "following" -> R.string.timeline_info_following
        else -> 0
    }
}

fun String.counterToImage(active: Boolean): Int {
    return when (this) {
        "likes" -> if (active) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
        "reposts" -> if (active) R.drawable.ic_baseline_repeat_on_24 else R.drawable.ic_baseline_repeat_24
        "replies" -> if (active) R.drawable.ic_baseline_chat_bubble_24 else R.drawable.ic_baseline_chat_bubble_outline_24
        else -> 0
    }
}

fun String.counterToColor(active: Boolean): Color? {
    return when (this) {
        "likes" -> if (active) likedColor else null
        "reposts" -> if (active) repostedColor else null
        else -> null
    }
}
