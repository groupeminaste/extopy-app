package com.extopy.features.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.extopy.models.users.User

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    // Properties

    private val accounts = MutableLiveData<List<User>>()

    // Getters

    fun getAccounts(): LiveData<List<User>> {
        return accounts
    }

    // Methods

    fun handleAccountClick(account: User) {

    }

}
