package me.nathanfallet.extopy.features.auth

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import me.nathanfallet.extopy.ui.theme.primaryColor
import me.nathanfallet.extopy.viewmodels.auth.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthView(
    onUserLogged: () -> Unit,
    modifier: Modifier = Modifier,
    code: String? = null,
) {

    val context = LocalContext.current

    val viewModel = koinViewModel<AuthViewModel>()

    LaunchedEffect(code) {
        code?.let {
            viewModel.authenticate(code, onUserLogged)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(primaryColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Time to get back to the basics.")

        Button(
            modifier = modifier,
            onClick = {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.url))
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ContextCompat.startActivity(context, browserIntent, null)
            }
        ) {
            Text("Authenticate")
        }
    }

}
