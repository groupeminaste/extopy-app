package com.extopy.viewmodels.root

import com.extopy.models.users.User
import com.extopy.usecases.auth.IGetCurrentUserUseCase
import com.extopy.usecases.auth.ISetTokenUseCase
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import dev.kaccelero.commons.exceptions.APIException
import io.ktor.http.*
import kotlinx.coroutines.flow.asStateFlow

class RootViewModel(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val setTokenUseCase: ISetTokenUseCase,
) : ViewModel() {

    // Properties

    private val _user = MutableStateFlow<User?>(viewModelScope, null)

    @NativeCoroutinesState
    val user = _user.asStateFlow()

    // Methods

    @NativeCoroutines
    suspend fun fetchUser() {
        try {
            _user.value = getCurrentUserUseCase()
        } catch (e: APIException) {
            if (e.code == HttpStatusCode.Unauthorized) {
                // Token is not valid anymore, remove it
                setTokenUseCase(null)
            }
        }
    }

}
