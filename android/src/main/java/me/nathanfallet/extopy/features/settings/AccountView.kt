package me.nathanfallet.extopy.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jamal.composeprefs.ui.prefs.TextPref
import me.nathanfallet.extopy.models.users.User

@Composable
fun AccountView(
    user: User,
    modifier: Modifier = Modifier,
) {
    TextPref(
        title = user.displayName,
        summary = user.username,
        modifier = modifier
    )
}
