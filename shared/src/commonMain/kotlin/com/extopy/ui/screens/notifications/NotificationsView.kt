package com.extopy.ui.screens.notifications

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.extopy.viewmodels.notifications.NotificationsViewModel
import extopy_app.shared.generated.resources.Res
import extopy_app.shared.generated.resources.notifications_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsView(
    modifier: Modifier = Modifier,
) {

    val viewModel = koinViewModel<NotificationsViewModel>()

    val notifications by viewModel.notifications.collectAsState()

    LazyColumn(
        modifier
    ) {
        item {
            TopAppBar(
                title = { Text(stringResource(Res.string.notifications_title)) }
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        items(notifications) {
            Text(it.body ?: "No body")
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

}
