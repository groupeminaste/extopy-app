package com.extopy.presentation.components.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.extopy.presentation.theme.CounterStyle
import com.extopy.presentation.util.simplify
import com.extopy.resources.Res
import com.extopy.resources.timeline_info_followers_label
import com.extopy.resources.timeline_info_following_label
import com.extopy.resources.timeline_info_posts_label
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * One of a profile's three figures.
 *
 * The number and its label are separate styles stacked on top of each other, rather than one string
 * broken by a newline: the figure is what the eye is looking for, and it should not be the same
 * weight as the word next to it.
 */
@Composable
fun UserCounterView(
    type: UserCounter,
    value: Long,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
    ) {
        Text(
            text = value.simplify(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = stringResource(labelForCounter(type)),
            style = CounterStyle,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

fun labelForCounter(type: UserCounter): StringResource = when (type) {
    UserCounter.POSTS -> Res.string.timeline_info_posts_label
    UserCounter.FOLLOWERS -> Res.string.timeline_info_followers_label
    UserCounter.FOLLOWING -> Res.string.timeline_info_following_label
}
