package com.extopy.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.extopy.models.users.User

@Composable
fun AccountView(
    user: User,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(user.displayName)
        Text(user.displayName)
    }
}
