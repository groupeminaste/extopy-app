package me.nathanfallet.extopy.ui.components.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.models.users.UserButton
import me.nathanfallet.extopy.models.users.UserCounter

@Composable
fun UserCard(
    user: User,
    viewedBy: User,
    navigate: (String) -> Unit,
    onPostsClicked: (User) -> Unit,
    onFollowersClicked: (User) -> Unit,
    onFollowingClicked: (User) -> Unit,
    onEditClicked: (User) -> Unit,
    onFollowClicked: (User) -> Unit,
    onSettingsClicked: (User) -> Unit,
    onDirectMessageClicked: (User) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                UserHeaderView(
                    user = user,
                    modifier = Modifier.clickable {
                        navigate("timeline/user/${user.id}")
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Text(user.biography ?: "")

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                UserCounterView(
                    type = UserCounter.POSTS,
                    value = user.postsCount ?: 0,
                    onClick = { onPostsClicked(user) }
                )
                UserCounterView(
                    type = UserCounter.FOLLOWERS,
                    value = user.followersCount ?: 0,
                    onClick = { onFollowersClicked(user) }
                )
                UserCounterView(
                    type = UserCounter.FOLLOWING,
                    value = user.followingCount ?: 0,
                    onClick = { onFollowingClicked(user) }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val button1 =
                    if (user.id == viewedBy.id) UserButton.EDIT
                    else if (user.followersIn == true) UserButton.FOLLOWING
                    else UserButton.FOLLOW
                val filledButton1 = button1 == UserButton.FOLLOWING || button1 == UserButton.ASKED
                val button2 =
                    if (user.id == viewedBy.id) UserButton.SETTINGS
                    else UserButton.DC

                UserButtonView(
                    type = button1,
                    filled = filledButton1,
                    onClick = {
                        if (button1 == UserButton.EDIT) onEditClicked(user)
                        else onFollowClicked(user)
                    },
                    modifier = Modifier.weight(1f)
                )
                UserButtonView(
                    type = button2,
                    filled = false,
                    onClick = {
                        if (button2 == UserButton.SETTINGS) onSettingsClicked(user)
                        else onDirectMessageClicked(user)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
