package com.extopy.ui.components.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.extopy.models.navigation.Route
import com.extopy.models.posts.Post
import com.extopy.models.posts.PostCounter
import com.extopy.ui.components.users.UserHeaderView
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun PostCard(
    post: Post,
    navigate: (Route) -> Unit,
    onLikeClicked: (Post) -> Unit,
    onRepostClicked: (Post) -> Unit,
    onReplyClicked: (Post) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp).clickable {
                navigate(Route.TimelinePost(post.id.toString()))
            }
        ) {
            Row {
                post.user?.let { user ->
                    UserHeaderView(
                        user = user,
                        modifier = Modifier.clickable {
                            navigate(Route.TimelineUser(user.id.toString()))
                        }
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    // TODO: Format date (time ago)
                    text = post.publishedAt.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(post.body ?: "")

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                PostCounterView(
                    type = PostCounter.LIKES,
                    value = post.likesCount ?: 0,
                    active = post.likesIn ?: false,
                    onClick = { onLikeClicked(post) }
                )
                PostCounterView(
                    type = PostCounter.REPOSTS,
                    value = post.repostsCount ?: 0,
                    active = false,
                    onClick = { onRepostClicked(post) }
                )
                PostCounterView(
                    type = PostCounter.REPLIES,
                    value = post.repliesCount ?: 0,
                    active = false,
                    onClick = { onReplyClicked(post) }
                )
            }
        }
    }
}
