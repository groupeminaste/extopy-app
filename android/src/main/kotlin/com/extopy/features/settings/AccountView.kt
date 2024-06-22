package com.extopy.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.extopy.models.users.User
import com.jamal.composeprefs3.ui.prefs.TextPref

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
