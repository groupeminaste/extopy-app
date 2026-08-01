package com.extopy.di

import com.extopy.client.ExtopyClient
import com.extopy.client.ExtopyClientImpl
import com.extopy.client.auth.TokenStore
import com.extopy.data.api.SettingsTokenStore
import com.extopy.data.api.baseUrl
import com.extopy.data.database.ExtopyDatabase
import com.extopy.data.repositories.ApiAuthRepository
import com.extopy.data.repositories.ApiPostsRepository
import com.extopy.data.repositories.ApiTimelinesRepository
import com.extopy.data.repositories.ApiUsersRepository
import com.extopy.data.repositories.RoomCacheRepository
import com.extopy.data.repositories.SettingsSessionRepository
import com.extopy.domain.models.application.ExtopyEnvironment
import com.extopy.domain.repositories.AuthRepository
import com.extopy.domain.repositories.CacheRepository
import com.extopy.domain.repositories.PostsRepository
import com.extopy.domain.repositories.SessionRepository
import com.extopy.domain.repositories.TimelinesRepository
import com.extopy.domain.repositories.UsersRepository
import com.russhwolf.settings.Settings
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Qualifier for the settings the session lives in — the keychain on iOS, shared preferences on
 * Android — as opposed to the general-purpose ones.
 */
val SecureSettings = named("secure")

/**
 * Where the app talks to the outside world: the HTTP client, the local database, and the
 * repositories that put the two together.
 *
 * The platform module is what supplies [ExtopyEnvironment], both [Settings] and the [ExtopyDatabase]
 * itself, since all three depend on things only the platform knows.
 */
val dataModule = module {
    single<TokenStore> { SettingsTokenStore(get(SecureSettings)) }
    single<ExtopyClient> {
        ExtopyClientImpl(
            baseUrl = get<ExtopyEnvironment>().baseUrl,
            tokenStore = get(),
        )
    }

    single { get<ExtopyDatabase>().userDao() }
    single { get<ExtopyDatabase>().postDao() }

    single<SessionRepository> { SettingsSessionRepository(get(SecureSettings)) }
    single<AuthRepository> { ApiAuthRepository(get(), get()) }
    single<UsersRepository> { ApiUsersRepository(get(), get(), get()) }
    single<PostsRepository> { ApiPostsRepository(get(), get(), get()) }
    single<TimelinesRepository> { ApiTimelinesRepository(get(), get(), get()) }
    single<CacheRepository> { RoomCacheRepository(get(), get()) }
}
