package com.extopy.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.extopy.models.navigation.Route
import com.extopy.ui.components.settings.AccountView
import com.extopy.viewmodels.settings.SettingsViewModel
import extopy_app.shared.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    navigate: (Route) -> Unit,
) {

    val uriHandler = LocalUriHandler.current

    val viewModel = koinViewModel<SettingsViewModel>()
    val accounts by viewModel.accounts.collectAsState()

    Column(modifier) {
        TopAppBar(
            title = { Text(stringResource(Res.string.settings_title)) }
        )
        LazyColumn {
            item {
                Text(
                    text = stringResource(Res.string.settings_accounts)
                )
            }
            for (account in accounts ?: listOf()) item {
                AccountView(
                    user = account,
                    modifier = Modifier.clickable {
                        viewModel.handleAccountClick(account)
                    }
                )
            }

            item {
                Text(
                    text = stringResource(Res.string.settings_about)
                )
            }
            item {
                Text(
                    text = stringResource(Res.string.settings_about_developer),
                    modifier = Modifier.clickable {
                        uriHandler.openUri("https://www.groupe-minaste.org/")
                    }
                )
            }
        }
    }

}
