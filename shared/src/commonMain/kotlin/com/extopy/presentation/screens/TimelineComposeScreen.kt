package com.extopy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.extopy.presentation.components.base.ExtopyTopBar
import com.extopy.presentation.theme.PillShape
import com.extopy.presentation.theme.PostBodyStyle
import com.extopy.presentation.viewmodels.TimelineComposeViewModel
import com.extopy.resources.Res
import com.extopy.resources.timeline_compose_content
import com.extopy.resources.timeline_compose_reply_title
import com.extopy.resources.timeline_compose_repost_title
import com.extopy.resources.timeline_compose_send
import com.extopy.resources.timeline_compose_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.uuid.Uuid

@Composable
fun TimelineComposeScreen(
    onPostComposed: () -> Unit,
    onBack: () -> Unit,
    repliedToId: Uuid?,
    repostOfId: Uuid?,
    modifier: Modifier = Modifier,
) {

    val viewModel = koinViewModel<TimelineComposeViewModel>(
        parameters = { parametersOf("", repliedToId, repostOfId) }
    )

    val body by viewModel.body.collectAsState()

    val title = when {
        repliedToId != null -> Res.string.timeline_compose_reply_title
        repostOfId != null -> Res.string.timeline_compose_repost_title
        else -> Res.string.timeline_compose_title
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ExtopyTopBar(title = stringResource(title), onBack = onBack)

        // A rounded surface rather than an OutlinedTextField, for the same reason the search field
        // is one: the stock field's indicator line and label slot fight a soft shape.
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .padding(14.dp),
        ) {
            if (body.isEmpty()) {
                Text(
                    text = stringResource(Res.string.timeline_compose_content),
                    style = PostBodyStyle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            BasicTextField(
                value = body,
                onValueChange = viewModel::updateBody,
                textStyle = PostBodyStyle.copy(color = MaterialTheme.colorScheme.onSurface),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
            )
        }

        Button(
            onClick = {
                viewModel.viewModelScope.launch {
                    viewModel.send()
                    onPostComposed()
                }
            },
            enabled = body.isNotBlank(),
            shape = PillShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 14.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(Res.string.timeline_compose_send),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }

}
