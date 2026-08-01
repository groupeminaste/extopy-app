package com.extopy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.extopy.presentation.viewmodels.NotificationsViewModel
import com.extopy.resources.Res
import com.extopy.resources.notifications_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
) {

    val viewModel = koinViewModel<NotificationsViewModel>()

    val notifications by viewModel.notifications.collectAsState()

    LazyColumn(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        item {
            CenterAlignedTopAppBar(
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
