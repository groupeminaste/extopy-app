package com.extopy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.extopy.presentation.components.base.EmptyState
import com.extopy.presentation.components.base.ExtopyTitleBar
import com.extopy.presentation.theme.PostBodyStyle
import com.extopy.presentation.theme.defaultCardColors
import com.extopy.presentation.viewmodels.NotificationsViewModel
import com.extopy.resources.Res
import com.extopy.resources.notifications_title
import com.extopy.resources.soon_body
import com.extopy.resources.soon_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
) {

    val viewModel = koinViewModel<NotificationsViewModel>()

    val notifications by viewModel.notifications.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        ExtopyTitleBar(title = stringResource(Res.string.notifications_title))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        ) {
            items(notifications) { notification ->
                Card(
                    shape = MaterialTheme.shapes.medium,
                    colors = defaultCardColors(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                ) {
                    Text(
                        text = notification.body.orEmpty(),
                        style = PostBodyStyle,
                        modifier = Modifier.padding(14.dp),
                    )
                }
            }
            if (notifications.isEmpty()) {
                item {
                    // Nothing produces notifications yet — the API does not expose them.
                    EmptyState(
                        title = stringResource(Res.string.soon_title),
                        body = stringResource(Res.string.soon_body),
                    )
                }
            }
        }
    }

}
