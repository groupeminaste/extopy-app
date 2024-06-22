package com.extopy.ui.components.users

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.extopy.R
import com.extopy.models.users.UserButton

@Composable
fun UserButtonView(
    type: UserButton,
    filled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val text = stringForButton(type)

    Button(
        onClick = onClick,
        border = if (filled) null else BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.primary
        ),
        colors = if (filled) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
        modifier = modifier
    ) {
        Text(text = stringResource(id = text))
    }

}

fun stringForButton(type: UserButton): Int {
    return when (type) {
        UserButton.EDIT -> R.string.timeline_button_edit
        UserButton.FOLLOW -> R.string.timeline_button_follow
        UserButton.FOLLOWING -> R.string.timeline_button_following
        UserButton.ASKED -> R.string.timeline_button_asked
        UserButton.SETTINGS -> R.string.timeline_button_settings
        UserButton.DC -> R.string.timeline_button_dc
    }
}
