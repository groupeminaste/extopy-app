package com.extopy.data.api

import com.extopy.client.auth.AuthTokens
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SettingsTokenStoreTest {

    @Test
    fun readsBackWhatItStored() = runTest {
        val store = SettingsTokenStore(MapSettings())
        store.save(AuthTokens("access", "refresh"))

        assertEquals(AuthTokens("access", "refresh"), store.load())
    }

    @Test
    fun hasNothingToLoadBeforeSigningIn() = runTest {
        assertNull(SettingsTokenStore(MapSettings()).load())
    }

    @Test
    fun hasNothingToLoadOnceCleared() = runTest {
        val store = SettingsTokenStore(MapSettings())
        store.save(AuthTokens("access", "refresh"))
        store.clear()

        assertNull(store.load())
    }

    @Test
    fun refusesHalfASession() = runTest {
        // A store holding an access token but no refresh token could not renew it, so treating it
        // as "signed in" would only produce a call that fails once and then signs the user out.
        val settings = MapSettings("accessToken" to "access")

        assertNull(SettingsTokenStore(settings).load())
    }

}
