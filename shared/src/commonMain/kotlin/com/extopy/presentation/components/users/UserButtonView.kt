package com.extopy.presentation.components.users

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.extopy.presentation.theme.PillShape
import com.extopy.resources.Res
import com.extopy.resources.timeline_button_ask
import com.extopy.resources.timeline_button_asked
import com.extopy.resources.timeline_button_dc
import com.extopy.resources.timeline_button_edit
import com.extopy.resources.timeline_button_follow
import com.extopy.resources.timeline_button_following
import com.extopy.resources.timeline_button_settings
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * An action on a profile.
 *
 * [filled] is what the button *does*, not how it looks by default: the primary action of the moment
 * is filled, the alternative is outlined, and the two swap once you follow someone.
 */
@Composable
fun UserButtonView(
    type: UserButton,
    filled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = PillShape,
        border = if (filled) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = if (filled) ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) else ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(stringForButton(type)),
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

fun stringForButton(type: UserButton): StringResource = when (type) {
    UserButton.EDIT -> Res.string.timeline_button_edit
    UserButton.FOLLOW -> Res.string.timeline_button_follow
    UserButton.FOLLOWING -> Res.string.timeline_button_following
    UserButton.ASKED -> Res.string.timeline_button_asked
    UserButton.SETTINGS -> Res.string.timeline_button_settings
    UserButton.DC -> Res.string.timeline_button_dc
}
