package com.extopy.ui.components.users

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.extopy.extensions.simplify
import com.extopy.models.users.UserCounter
import extopy_app.shared.generated.resources.Res
import extopy_app.shared.generated.resources.timeline_info_followers
import extopy_app.shared.generated.resources.timeline_info_following
import extopy_app.shared.generated.resources.timeline_info_posts
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
