package com.extopy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.extopy.presentation.components.base.EmptyState
import com.extopy.presentation.components.base.ExtopyTitleBar
import com.extopy.resources.Res
import com.extopy.resources.direct_message_title
import com.extopy.resources.soon_body
import com.extopy.resources.soon_title
import org.jetbrains.compose.resources.stringResource

/**
 * Direct messages.
 *
 * The API has no conversations endpoint yet, so this says so instead of rendering nothing — the tab
 * used to open onto a blank screen, which reads as a bug rather than as an unfinished feature.
 */
@Composable
fun DirectMessagesScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        ExtopyTitleBar(title = stringResource(Res.string.direct_message_title))
        EmptyState(
            title = stringResource(Res.string.soon_title),
            body = stringResource(Res.string.soon_body),
        )
    }
}
