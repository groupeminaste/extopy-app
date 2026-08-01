package com.extopy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.extopy.domain.models.application.ApiException
import com.extopy.domain.models.users.User
import com.extopy.domain.usecases.auth.GetCurrentUserUseCase
import com.extopy.domain.usecases.auth.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Decides whether the app shows the timelines or the sign-in screen, by answering one question:
 * is there a user behind the session we are holding?
 */
class RootViewModel(
    private val getCurrentUser: GetCurrentUserUseCase,
    private val logout: LogoutUseCase,
) : ViewModel() {

    // Properties

    private val _user = MutableStateFlow<User?>(null)

    val user = _user.asStateFlow()

    // Methods

    suspend fun fetchUser() {
        try {
            _user.value = getCurrentUser()
        } catch (e: ApiException) {
            // The client already tried to refresh and was turned down: this session is not coming
            // back, so drop it rather than retry it on every screen.
            if (e.unauthorized) {
                logout()
                _user.value = null
            }
        }
    }

}
