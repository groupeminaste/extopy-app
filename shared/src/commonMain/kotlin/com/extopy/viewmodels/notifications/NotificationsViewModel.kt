package com.extopy.viewmodels.notifications

import com.extopy.models.notifications.Notification
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.MutableStateFlow
import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.flow.asStateFlow

class NotificationsViewModel : ViewModel() {

    // Properties

    private val _notifications = MutableStateFlow<List<Notification>>(viewModelScope, listOf())

    @NativeCoroutinesState
    val notifications = _notifications.asStateFlow()

    // Methods

    @NativeCoroutines
    suspend fun fetchNotifications() {

    }

}
