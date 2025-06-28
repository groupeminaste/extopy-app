package com.extopy.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.extopy.ui.components.auth.AuthRootView
import com.extopy.viewmodels.auth.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthView(
    onUserLogged: () -> Unit,
    modifier: Modifier = Modifier,
    code: String? = null,
) {

    val uriHandler = LocalUriHandler.current

    val viewModel = koinViewModel<AuthViewModel>()

    val loginRegisterClicked = {
        uriHandler.openUri(viewModel.url)
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
