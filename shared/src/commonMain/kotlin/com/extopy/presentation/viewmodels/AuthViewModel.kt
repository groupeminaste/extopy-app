package com.extopy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.extopy.domain.models.application.ApiException
import com.extopy.domain.repositories.AuthRepository
import com.extopy.domain.usecases.auth.AuthenticateUseCase

/**
 * Sends the user to the web authorization page, and turns the code it redirects back with into a
 * session.
 */
class AuthViewModel(
    auth: AuthRepository,
    private val authenticateUseCase: AuthenticateUseCase,
) : ViewModel() {

    val url = auth.authorizationUrl

    suspend fun authenticate(code: String, onUserLogged: () -> Unit) {
        try {
            authenticateUseCase(code) ?: return
            onUserLogged()
        } catch (e: ApiException) {
            // A code the server refuses is not something the user can act on — it has expired or
            // was already spent. Leaving the screen as it is lets them simply try again.
        }
    }

}
