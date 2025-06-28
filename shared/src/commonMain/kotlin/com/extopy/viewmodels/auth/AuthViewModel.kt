package com.extopy.viewmodels.auth

import androidx.lifecycle.ViewModel
import com.extopy.models.application.ExtopyEnvironment
import com.extopy.usecases.auth.IFetchTokenUseCase
import com.extopy.usecases.auth.IGetCurrentUserUseCase
import dev.kaccelero.commons.exceptions.APIException

class AuthViewModel(
    environment: ExtopyEnvironment,
    private val fetchTokenUseCase: IFetchTokenUseCase,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
) : ViewModel() {

    val url = environment.baseUrl + "/auth/authorize?clientId=00000000-0000-0000-0000-000000000000"

    suspend fun authenticate(code: String, onUserLogged: () -> Unit) {
        try {
            fetchTokenUseCase(code) ?: return
            getCurrentUserUseCase()

            onUserLogged()
        } catch (e: APIException) {

        }
    }

}
