package me.nathanfallet.extopy.features.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jamal.composeprefs3.ui.GroupHeader
import com.jamal.composeprefs3.ui.PrefsScreen
import com.jamal.composeprefs3.ui.prefs.TextPref
import me.nathanfallet.extopy.R
import me.nathanfallet.extopy.extensions.dataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    navigate: (String) -> Unit,
    code: String? = null,
) {

    val viewModel: SettingsViewModel = viewModel<SettingsViewModel>()
    val accounts by viewModel.getAccounts().observeAsState()

    Column(modifier) {
        TopAppBar(
            title = { Text(stringResource(id = R.string.settings_title)) }
        )
        PrefsScreen(
            dataStore = LocalContext.current.dataStore
        ) {
            prefsGroup({
                GroupHeader(
                    title = stringResource(id = R.string.settings_accounts)
                )
            }) {
                for (account in accounts ?: listOf()) {
                    prefsItem {
                        AccountView(
                            user = account,
                            modifier = Modifier.clickable {
                                viewModel.handleAccountClick(account)
                            }
                        )
                    }
                }
            }

            prefsGroup({
                GroupHeader(
                    title = stringResource(id = R.string.settings_about)
                )
            }) {
                prefsItem {
                    TextPref(
                        title = stringResource(id = R.string.settings_about_developer),
                        onClick = {
                            val browserIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.groupe-minaste.org/")
                            )
                            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(viewModel.getApplication(), browserIntent, null)
                        },
                        enabled = true
                    )
                }
            }
        }
    }

}
