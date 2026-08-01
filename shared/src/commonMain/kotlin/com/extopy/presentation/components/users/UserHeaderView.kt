package com.extopy.presentation.components.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.extopy.domain.models.users.User
import com.extopy.presentation.theme.AuthorNameStyle
import com.extopy.presentation.theme.HandleStyle
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Fill
import com.adamglin.phosphoricons.fill.SealCheck
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.uuid.Uuid

/**
 * The author of something: avatar, name, handle.
 *
 * The name and the handle are deliberately different sizes and weights — they used to share the
 * body style, which left nothing telling the eye which of the two was the person.
 */
@Composable
fun UserHeaderView(
    user: User,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        AsyncImage(
            model = user.avatar,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                // Shows through while the avatar loads, and stands in when there is none.
                .background(MaterialTheme.colorScheme.surfaceVariant),
        )
        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = user.displayName,
                    style = AuthorNameStyle,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (user.verified == true) {
                    Icon(
                        imageVector = PhosphorIcons.Fill.SealCheck,
                        contentDescription = "Verified",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(15.dp),
                    )
                }
            }
            Text(
                text = "@${user.username}",
                style = HandleStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun UserHeaderViewPreview() {
    UserHeaderView(
        user = User(
            id = Uuid.random(),
            displayName = "John Doe",
            username = "johndoe",
            avatar = "https://example.com/avatar.jpg",
            verified = true,
        ),
    )
}
