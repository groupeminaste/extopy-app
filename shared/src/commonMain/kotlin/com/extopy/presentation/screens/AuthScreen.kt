package com.extopy.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.extopy.presentation.components.auth.AuthRootView
import com.extopy.presentation.viewmodels.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
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
