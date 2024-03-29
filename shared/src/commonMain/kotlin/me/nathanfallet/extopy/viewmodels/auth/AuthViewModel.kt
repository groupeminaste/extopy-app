package me.nathanfallet.extopy.viewmodels.auth

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import me.nathanfallet.extopy.models.application.ExtopyEnvironment
import me.nathanfallet.extopy.usecases.auth.IFetchTokenUseCase
import me.nathanfallet.extopy.usecases.auth.ISetTokenUseCase
import me.nathanfallet.extopy.usecases.auth.ISetUserIdUseCase
import me.nathanfallet.extopy.usecases.users.IFetchUserUseCase
import me.nathanfallet.ktorx.models.exceptions.APIException

class AuthViewModel(
    environment: ExtopyEnvironment,
    private val fetchTokenUseCase: IFetchTokenUseCase,
    private val setTokenUseCase: ISetTokenUseCase,
    private val setUserIdUseCase: ISetUserIdUseCase,
    private val fetchUserUseCase: IFetchUserUseCase,
) : KMMViewModel() {

    val url = environment.baseUrl + "/auth/authorize?clientId=extopy"

    @NativeCoroutines
    suspend fun authenticate(code: String, onUserLogged: () -> Unit) {
        try {
            val token = fetchTokenUseCase(code) ?: return
            val userId = token.idToken ?: return
            setTokenUseCase(token.accessToken)
            setUserIdUseCase(userId)
            fetchUserUseCase(userId)

            onUserLogged()
        } catch (e: APIException) {

        }
    }

}
