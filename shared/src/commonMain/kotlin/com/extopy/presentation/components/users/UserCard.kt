package com.extopy.presentation.components.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.extopy.domain.models.users.User
import com.extopy.presentation.navigation.Route
import com.extopy.presentation.theme.PostBodyStyle
import com.extopy.presentation.theme.defaultCardColors

/**
 * A user, as a card: who they are, their three figures, and the two things you can do to them.
 *
 * Flat like [com.extopy.presentation.components.posts.PostCard], and the counters are spread evenly
 * here on purpose — unlike a post's, these three *are* the content of the row, not metadata under
 * something else.
 */
@Composable
fun UserCard(
    user: User,
    viewedBy: User,
    navigate: (Route) -> Unit,
    onPostsClicked: (User) -> Unit,
    onFollowersClicked: (User) -> Unit,
    onFollowingClicked: (User) -> Unit,
    onEditClicked: (User) -> Unit,
    onFollowClicked: (User) -> Unit,
    onSettingsClicked: (User) -> Unit,
    onDirectMessageClicked: (User) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = defaultCardColors(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.padding(14.dp),
        ) {
            UserHeaderView(
                user = user,
                modifier = Modifier.clickable {
                    navigate(Route.TimelineUser(user.id.toString()))
                },
            )

            // A profile with no biography should not leave an empty line behind.
            user.biography?.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    style = PostBodyStyle,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                UserCounterView(
                    type = UserCounter.POSTS,
                    value = user.postsCount ?: 0,
                    onClick = { onPostsClicked(user) },
                    modifier = Modifier.weight(1f),
                )
                UserCounterView(
                    type = UserCounter.FOLLOWERS,
                    value = user.followersCount ?: 0,
                    onClick = { onFollowersClicked(user) },
                    modifier = Modifier.weight(1f),
                )
                UserCounterView(
                    type = UserCounter.FOLLOWING,
                    value = user.followingCount ?: 0,
                    onClick = { onFollowingClicked(user) },
                    modifier = Modifier.weight(1f),
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val isSelf = user.id == viewedBy.id
                val primary = when {
                    isSelf -> UserButton.EDIT
                    user.followersIn == true -> UserButton.FOLLOWING
                    else -> UserButton.FOLLOW
                }
                val secondary = if (isSelf) UserButton.SETTINGS else UserButton.DC

                UserButtonView(
                    type = primary,
                    // "Follow" is the action being offered, so it is the filled one; once you
                    // already follow, the button is a state and steps back to an outline.
                    filled = primary == UserButton.FOLLOW,
                    onClick = {
                        if (primary == UserButton.EDIT) onEditClicked(user) else onFollowClicked(user)
                    },
                    modifier = Modifier.weight(1f),
                )
                UserButtonView(
                    type = secondary,
                    filled = false,
                    onClick = {
                        if (secondary == UserButton.SETTINGS) onSettingsClicked(user)
                        else onDirectMessageClicked(user)
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}
