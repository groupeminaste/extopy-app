package com.extopy.presentation.components.users

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.extopy.presentation.util.simplify
import com.extopy.presentation.components.users.UserCounter
import com.extopy.resources.Res
import com.extopy.resources.timeline_info_followers
import com.extopy.resources.timeline_info_following
import com.extopy.resources.timeline_info_posts
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserCounterView(
    type: UserCounter,
    value: Long,
    onClick: () -> Unit,
) {

    Text(
        text = stringResource(stringForCounter(type), value.simplify()),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.clickable(onClick = onClick)
    )

}

fun stringForCounter(type: UserCounter): StringResource = when (type) {
    UserCounter.POSTS -> Res.string.timeline_info_posts
    UserCounter.FOLLOWERS -> Res.string.timeline_info_followers
    UserCounter.FOLLOWING -> Res.string.timeline_info_following
}
