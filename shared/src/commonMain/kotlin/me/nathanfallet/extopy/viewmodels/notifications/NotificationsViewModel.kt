package me.nathanfallet.extopy.viewmodels.notifications

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import me.nathanfallet.extopy.models.notifications.Notification

class NotificationsViewModel : KMMViewModel() {

    // Properties

    private val _notifications = MutableStateFlow<List<Notification>>(viewModelScope, listOf())

    @NativeCoroutinesState
    val notifications = _notifications.asStateFlow()

    // Methods

    @NativeCoroutines
    suspend fun fetchNotifications() {

    }

}
