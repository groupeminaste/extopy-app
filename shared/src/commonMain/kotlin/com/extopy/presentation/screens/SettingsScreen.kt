package com.extopy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.extopy.presentation.components.base.ExtopyTitleBar
import com.extopy.presentation.components.settings.AccountView
import com.extopy.presentation.theme.defaultCardColors
import com.extopy.presentation.viewmodels.SettingsViewModel
import com.extopy.resources.Res
import com.extopy.resources.settings_about
import com.extopy.resources.settings_about_developer
import com.extopy.resources.settings_accounts
import com.extopy.resources.settings_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
) {

    val uriHandler = LocalUriHandler.current

    val viewModel = koinViewModel<SettingsViewModel>()
    val accounts by viewModel.accounts.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        ExtopyTitleBar(title = stringResource(Res.string.settings_title))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        ) {
            // The accounts section only exists once there is something in it: an empty headed
            // section reads as a list that failed to load.
            accounts?.takeIf { it.isNotEmpty() }?.let { list ->
                item { SectionHeader(stringResource(Res.string.settings_accounts)) }
                item {
                    SettingsCard {
                        list.forEach { account ->
                            AccountView(
                                user = account,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.handleAccountClick(account) }
                                    .padding(14.dp),
                            )
                        }
                    }
                }
            }

            item { SectionHeader(stringResource(Res.string.settings_about)) }
            item {
                SettingsCard {
                    Text(
                        text = stringResource(Res.string.settings_about_developer),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { uriHandler.openUri("https://www.groupe-minaste.org/") }
                            .padding(14.dp),
                    )
                }
            }
        }
    }

}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(start = 6.dp, top = 12.dp, bottom = 2.dp),
    )
}

/** Groups rows into one rounded surface, the way a settings list reads on both platforms. */
@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = defaultCardColors(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column { content() }
    }
}
