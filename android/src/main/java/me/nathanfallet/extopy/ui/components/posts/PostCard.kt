package me.nathanfallet.extopy.ui.components.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.nathanfallet.extopy.R
import me.nathanfallet.extopy.models.posts.Post

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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable {
                        navigate("timeline/user/${post.userId}")
                    }
                ) {
                    AsyncImage(
                        model = post.user?.avatar,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(post.user?.displayName ?: "")
                        if (post.user?.verified == true) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_verified_24),
                                contentDescription = "Verified",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                                modifier = Modifier
                                    .size(MaterialTheme.typography.bodyMedium.fontSize.value.dp)
                            )
                        }
                    }
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
