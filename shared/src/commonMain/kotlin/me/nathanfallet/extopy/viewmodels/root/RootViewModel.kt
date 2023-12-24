package me.nathanfallet.extopy.viewmodels.root

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.usecases.auth.IGetUserIdUseCase
import me.nathanfallet.extopy.usecases.users.IFetchUserUseCase

class RootViewModel(
    private val getUserIdUseCase: IGetUserIdUseCase,
    private val fetchUserUseCase: IFetchUserUseCase,
) : KMMViewModel() {

    // Properties

    private val _user = MutableStateFlow<User?>(viewModelScope, null)

    @NativeCoroutinesState
    val user = _user.asStateFlow()

    // Methods

    @NativeCoroutines
    suspend fun fetchUser() {
        val userId = getUserIdUseCase() ?: return
        _user.value = fetchUserUseCase(userId)
    }

}
