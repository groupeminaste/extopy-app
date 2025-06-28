package com.extopy.viewmodels.notifications

import androidx.lifecycle.ViewModel
import com.extopy.models.notifications.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NotificationsViewModel : ViewModel() {

    // Properties

    private val _notifications = MutableStateFlow<List<Notification>>(listOf())

    val notifications = _notifications.asStateFlow()

    // Methods

    suspend fun fetchNotifications() {

    }

}
