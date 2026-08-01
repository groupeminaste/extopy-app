package com.extopy.presentation.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.extopy.domain.models.users.User
import com.extopy.presentation.components.users.UserHeaderView

/**
 * One account in the settings list.
 *
 * It used to print the display name twice — the second line was meant to be the handle, which
 * [UserHeaderView] now draws properly alongside the avatar.
 */
@Composable
fun AccountView(
    user: User,
    modifier: Modifier = Modifier,
) {
    UserHeaderView(user = user, modifier = modifier)
}
