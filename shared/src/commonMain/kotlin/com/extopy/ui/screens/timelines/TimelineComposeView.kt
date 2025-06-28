package com.extopy.ui.screens.timelines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.extopy.viewmodels.timelines.TimelineComposeViewModel
import dev.kaccelero.models.UUID
import extopy_app.shared.generated.resources.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineComposeView(
    onPostComposed: () -> Unit,
    repliedToId: UUID?,
    repostOfId: UUID?,
    modifier: Modifier,
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(stringResource(title)) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = body,
            onValueChange = viewModel::updateBody,
            placeholder = {
                Text(
                    text = stringResource(Res.string.timeline_compose_content),
                    color = Color.LightGray
                )
            }
        )
        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            enabled = body.isNotBlank(),
            onClick = {
                viewModel.viewModelScope.launch {
                    viewModel.send()
                    onPostComposed()
                }
            }
        ) {
            Text(text = stringResource(Res.string.timeline_compose_send))
        }
        Spacer(modifier = Modifier.height(8.dp))
    }

}
