package me.nathanfallet.extopy.ui.components.users

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.nathanfallet.extopy.R
import me.nathanfallet.extopy.models.users.User

@Composable
fun UserCard(
    user: User,
    viewedBy: User?,
    navigate: (String) -> Unit,
    counterClick: (User, String) -> Unit,
    buttonClick: (User, Int) -> Unit,
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable {
                        navigate("timeline/user/${user.id}")
                    }
                ) {
                    AsyncImage(
                        model = user.avatar,
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
                        Text(user.displayName)
                        if (user.verified == true) {
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
            }

            Text(user.biography ?: "")

            Row {
                Spacer(modifier = Modifier.weight(1f))
                /*
                for (counter in user.counters) {
                    Text(
                        text = stringResource(id = counter.id.counterToString()).format(counter.value.simplify()),
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable {
                                counterClick(user, counter.id)
                            }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                */
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val textButton1 =
                    if (user.id == viewedBy?.id) R.string.timeline_button_edit
                    else if (user.followersIn == true) R.string.timeline_button_following
                    else R.string.timeline_button_follow
                val filledButton1 =
                    textButton1 == R.string.timeline_button_following || textButton1 == R.string.timeline_button_asked
                val textButton2 =
                    if (user.id == viewedBy?.id) R.string.timeline_button_settings
                    else R.string.timeline_button_dc
                val filledButton2 =
                    false
                Button(
                    onClick = {
                        buttonClick(user, textButton1)
                    },
                    border = if (filledButton1) null else BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary
                    ),
                    colors = if (filledButton1) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(id = textButton1))
                }
                Button(
                    onClick = {
                        buttonClick(user, textButton2)
                    },
                    border = if (filledButton2) null else BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary
                    ),
                    colors = if (filledButton2) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(id = textButton2))
                }
            }
        }
    }
}
