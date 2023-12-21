package me.nathanfallet.extopy.features.notifications

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.nathanfallet.extopy.R
import me.nathanfallet.extopy.viewmodels.notifications.NotificationsViewModel
import org.koin.androidx.compose.koinViewModel

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
                title = { Text(stringResource(id = R.string.notifications_title)) }
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
