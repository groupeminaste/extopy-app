package me.nathanfallet.extopy.viewmodels.root

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.usecases.auth.IGetUserIdUseCase
import me.nathanfallet.extopy.usecases.users.IFetchUserUseCase

class RootViewModel(
    private val getUserIdUseCase: IGetUserIdUseCase,
    private val fetchUserUseCase: IFetchUserUseCase,
) : KMMViewModel() {

    // Properties

    private val _user = MutableStateFlow<User?>(viewModelScope, null)
    val user = _user.asStateFlow()

    // Methods

    init {
        fetchUser()
    }

    fun fetchUser() {
        viewModelScope.coroutineScope.launch {
            val userId = getUserIdUseCase() ?: return@launch
            _user.value = fetchUserUseCase(userId)
        }
    }

}
