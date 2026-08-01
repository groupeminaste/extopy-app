package com.extopy.data.api

import com.extopy.client.auth.AuthTokens
import com.extopy.client.auth.TokenStore
import com.russhwolf.settings.Settings

/**
 * Keeps the session tokens in key-value settings.
 *
 * Which settings those are is the platform's call: iOS passes a keychain-backed [Settings] so the
 * tokens are stored the way credentials should be, Android passes its shared preferences. The
 * client never knows the difference — it only ever calls [load], [save] and [clear].
 */
class SettingsTokenStore(
    private val settings: Settings,
) : TokenStore {

    override suspend fun load(): AuthTokens? {
        val accessToken = settings.getStringOrNull(KEY_ACCESS_TOKEN) ?: return null
        val refreshToken = settings.getStringOrNull(KEY_REFRESH_TOKEN) ?: return null
        return AuthTokens(accessToken, refreshToken)
    }

    override suspend fun save(tokens: AuthTokens) {
        settings.putString(KEY_ACCESS_TOKEN, tokens.accessToken)
        settings.putString(KEY_REFRESH_TOKEN, tokens.refreshToken)
    }

    override suspend fun clear() {
        settings.remove(KEY_ACCESS_TOKEN)
        settings.remove(KEY_REFRESH_TOKEN)
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "accessToken"
        private const val KEY_REFRESH_TOKEN = "refreshToken"
    }

}
