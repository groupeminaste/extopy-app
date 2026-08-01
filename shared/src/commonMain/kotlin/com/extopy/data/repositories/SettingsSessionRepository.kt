package com.extopy.data.repositories

import com.extopy.domain.repositories.SessionRepository
import com.russhwolf.settings.Settings
import kotlin.uuid.Uuid

/**
 * Remembers who is signed in, in key-value settings.
 *
 * Stored alongside the tokens rather than in the general settings: the two are one session, and
 * keeping them together means signing out cannot leave half of it behind.
 */
class SettingsSessionRepository(
    private val settings: Settings,
) : SessionRepository {

    override fun getUserId(): Uuid? =
        settings.getStringOrNull(KEY_USER_ID)?.let { runCatching { Uuid.parse(it) }.getOrNull() }

    override fun setUserId(userId: Uuid?) {
        if (userId == null) settings.remove(KEY_USER_ID)
        else settings.putString(KEY_USER_ID, userId.toString())
    }

    companion object {
        private const val KEY_USER_ID = "userId"
    }

}
