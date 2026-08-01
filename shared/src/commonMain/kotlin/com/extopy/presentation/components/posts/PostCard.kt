package com.extopy.presentation.components.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.extopy.domain.models.posts.Post
import com.extopy.presentation.components.users.UserHeaderView
import com.extopy.presentation.navigation.Route
import com.extopy.presentation.theme.PostBodyStyle
import com.extopy.presentation.theme.TimestampStyle
import com.extopy.presentation.theme.defaultCardColors
import com.extopy.presentation.util.timeAgo

/**
 * A post.
 *
 * Flat on purpose: the card is told apart from the background by its colour and its corners, not by
 * a shadow. The counters sit grouped on the left rather than spread across the width, so they read
 * as belonging to the text above them.
 */
@Composable
fun PostCard(
    post: Post,
    navigate: (Route) -> Unit,
    onLikeClicked: (Post) -> Unit,
    onRepostClicked: (Post) -> Unit,
    onReplyClicked: (Post) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = defaultCardColors(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .clickable { navigate(Route.TimelinePost(post.id.toString())) }
                .padding(14.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                post.user?.let { user ->
                    UserHeaderView(
                        user = user,
                        modifier = Modifier.clickable {
                            navigate(Route.TimelineUser(user.id.toString()))
                        },
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = post.publishedAt.timeAgo,
                    style = TimestampStyle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // A repost with nothing added has no body of its own; drawing an empty line for it would
            // leave a gap that looks like a rendering fault.
            post.body?.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    style = PostBodyStyle,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                PostCounterView(
                    type = PostCounter.LIKES,
                    value = post.likesCount ?: 0,
                    active = post.likesIn ?: false,
                    onClick = { onLikeClicked(post) },
                )
                PostCounterView(
                    type = PostCounter.REPOSTS,
                    value = post.repostsCount ?: 0,
                    active = false,
                    onClick = { onRepostClicked(post) },
                )
                PostCounterView(
                    type = PostCounter.REPLIES,
                    value = post.repliesCount ?: 0,
                    active = false,
                    onClick = { onReplyClicked(post) },
                )
            }
        }
    }
}
