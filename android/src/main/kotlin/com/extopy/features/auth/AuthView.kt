package com.extopy.features.auth

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.extopy.ui.components.auth.AuthRootView
import com.extopy.viewmodels.auth.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
@Suppress("FunctionName")
fun AuthView(
    onUserLogged: () -> Unit,
    modifier: Modifier = Modifier,
    code: String? = null,
) {

    val context = LocalContext.current

    val viewModel = koinViewModel<AuthViewModel>()

    val loginRegisterClicked = {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.url))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ContextCompat.startActivity(context, browserIntent, null)
    }

    LaunchedEffect(code) {
        code?.let {
            viewModel.authenticate(code, onUserLogged)
        }
    }

    AuthRootView(
        loginClicked = loginRegisterClicked,
        modifier = modifier
    )

}
