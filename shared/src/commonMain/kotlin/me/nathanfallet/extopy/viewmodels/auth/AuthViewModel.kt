package me.nathanfallet.extopy.viewmodels.auth

import com.rickclephas.kmm.viewmodel.KMMViewModel
import me.nathanfallet.extopy.models.application.ExtopyEnvironment
import me.nathanfallet.extopy.usecases.auth.IFetchTokenUseCase
import me.nathanfallet.extopy.usecases.auth.ISetTokenUseCase
import me.nathanfallet.extopy.usecases.auth.ISetUserIdUseCase
import me.nathanfallet.extopy.usecases.users.IFetchUserUseCase

class AuthViewModel(
    environment: ExtopyEnvironment,
    private val fetchTokenUseCase: IFetchTokenUseCase,
    private val setTokenUseCase: ISetTokenUseCase,
    private val setUserIdUseCase: ISetUserIdUseCase,
    private val fetchUserUseCase: IFetchUserUseCase,
) : KMMViewModel() {

    val url = environment.baseUrl + "/auth/authorize?client_id=extopy"

    suspend fun authenticate(code: String) {
        val token = fetchTokenUseCase(code) ?: return
        val userId = token.idToken ?: return
        setTokenUseCase(token.accessToken)
        setUserIdUseCase(userId)
        fetchUserUseCase(userId)

        // TODO: Find a way to update the UI and go back to home
    }

}
