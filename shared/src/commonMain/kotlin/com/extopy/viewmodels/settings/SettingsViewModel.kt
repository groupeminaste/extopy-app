package com.extopy.viewmodels.settings

import androidx.lifecycle.ViewModel
import com.extopy.models.users.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {

    // Properties

    private val _accounts = MutableStateFlow<List<User>?>(null)

    val accounts = _accounts.asStateFlow()

    // Methods

    fun handleAccountClick(account: User) {

    }

}
