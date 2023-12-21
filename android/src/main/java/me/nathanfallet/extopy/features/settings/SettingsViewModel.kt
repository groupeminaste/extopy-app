package me.nathanfallet.extopy.features.settings

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.nathanfallet.extopy.models.users.User

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    // Storage

    companion object {
        private var authenticationAccount: User? = null
    }

    // Properties

    private val accounts = MutableLiveData<List<User>>()

    // Getters

    fun getAccounts(): LiveData<List<User>> {
        return accounts
    }

    // Methods

    fun load(code: String?): SettingsViewModel {
        loadAccounts()
        code?.let {
            authenticate(it)
        }
        return this
    }

    fun loadAccounts() {
        // Fetch accounts

        // As they were reloaded, check them
        viewModelScope.launch {
        }
    }

    fun handleAccountClick(account: User) {
        //if (account.needsLogin) {
        viewModelScope.launch {
            loadAuthenticationURL(account)
        }
        //}
    }

    suspend fun loadAuthenticationURL(account: User) {
        try {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("")
            )
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            authenticationAccount = account
            ContextCompat.startActivity(getApplication(), browserIntent, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun authenticate(code: String) {
        authenticationAccount?.let { account ->
            viewModelScope.launch {
                /*
                account.authenticate(isSolar, code)
                database.updateAccount(
                    account.name,
                    account.token,
                    account.id
                )
                */
                loadAccounts()
            }
        }
    }

}
