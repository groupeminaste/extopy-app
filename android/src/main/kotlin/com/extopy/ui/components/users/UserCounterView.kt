package com.extopy.ui.components.users

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.extopy.R
import com.extopy.extensions.simplify
import com.extopy.models.users.UserCounter

@Composable
fun UserCounterView(
    type: UserCounter,
    value: Long,
    onClick: () -> Unit,
) {

    Text(
        text = stringResource(id = stringForCounter(type)).format(value.simplify()),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.clickable(onClick = onClick)
    )

}

fun stringForCounter(type: UserCounter): Int {
    return when (type) {
        UserCounter.POSTS -> R.string.timeline_info_posts
        UserCounter.FOLLOWERS -> R.string.timeline_info_followers
        UserCounter.FOLLOWING -> R.string.timeline_info_following
    }
}
