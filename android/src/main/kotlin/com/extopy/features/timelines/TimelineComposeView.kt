package com.extopy.features.timelines

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.extopy.R
import com.extopy.viewmodels.timelines.TimelineComposeViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import dev.kaccelero.models.UUID
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineComposeView(
    modifier: Modifier,
    onPostComposed: () -> Unit,
    repliedToId: UUID?,
    repostOfId: UUID?,
) {

    val viewModel = koinViewModel<TimelineComposeViewModel>(
        parameters = { parametersOf("", repliedToId, repostOfId) }
    )

    val body by viewModel.body.collectAsState()

    val title = when {
        repliedToId != null -> R.string.timeline_compose_reply_title
        repostOfId != null -> R.string.timeline_compose_repost_title
        else -> R.string.timeline_compose_title
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TopAppBar(
            title = { Text(stringResource(id = title)) }
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
                    text = stringResource(id = R.string.timeline_compose_content),
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
                viewModel.viewModelScope.coroutineScope.launch {
                    viewModel.send()
                    onPostComposed()
                }
            }
        ) {
            Text(text = stringResource(id = R.string.timeline_compose_send))
        }
        Spacer(modifier = Modifier.height(8.dp))
    }

}
