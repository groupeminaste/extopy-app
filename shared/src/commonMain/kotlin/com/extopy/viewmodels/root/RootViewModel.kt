package com.extopy.viewmodels.root

import androidx.lifecycle.ViewModel
import com.extopy.models.users.User
import com.extopy.usecases.auth.IGetCurrentUserUseCase
import com.extopy.usecases.auth.ISetTokenUseCase
import dev.kaccelero.commons.exceptions.APIException
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RootViewModel(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val setTokenUseCase: ISetTokenUseCase,
) : ViewModel() {

    // Properties

    private val _user = MutableStateFlow<User?>(null)

    val user = _user.asStateFlow()

    // Methods

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
