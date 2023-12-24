package me.nathanfallet.extopy.ui.components.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.ui.components.users.UserHeaderView

@Composable
fun PostCard(
    post: Post,
    navigate: (String) -> Unit,
    counterClick: (Post, String) -> Unit,
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
                navigate("timeline/post/${post.id}")
            }
        ) {
            Row {
                post.user?.let { user ->
                    UserHeaderView(
                        user = user,
                        modifier = Modifier.clickable {
                            navigate("timeline/user/${user.id}")
                        }
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    // TODO: Format date (time ago)
                    text = post.published?.toLocalDateTime(TimeZone.currentSystemDefault())?.date.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(post.body ?: "")

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                /*
                for (counter in post.counters) {
                    Image(
                        painter = painterResource(id = counter.id.counterToImage(counter.active)),
                        contentDescription = counter.id,
                        colorFilter = ColorFilter.tint(
                            counter.id.counterToColor(counter.active)
                                ?: MaterialTheme.colors.onSurface
                        ),
                        modifier = Modifier
                            .size(MaterialTheme.typography.body2.fontSize.value.dp)
                    )
                    Text(
                        text = counter.value.simplify(),
                        style = MaterialTheme.typography.body2,
                        color = counter.id.counterToColor(counter.active)
                            ?: MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .clickable {
                                counterClick(post, counter.id)
                            }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                */
            }
        }
    }
}
