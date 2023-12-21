package me.nathanfallet.extopy.di

import me.nathanfallet.extopy.client.ExtopyClient
import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.usecases.auth.*
import me.nathanfallet.extopy.usecases.timelines.FetchTimelineUseCase
import me.nathanfallet.extopy.usecases.timelines.IFetchTimelineUseCase
import me.nathanfallet.extopy.usecases.users.FetchUserUseCase
import me.nathanfallet.extopy.usecases.users.IFetchUserUseCase
import me.nathanfallet.extopy.viewmodels.auth.AuthViewModel
import me.nathanfallet.extopy.viewmodels.notifications.NotificationsViewModel
import me.nathanfallet.extopy.viewmodels.root.RootViewModel
import me.nathanfallet.extopy.viewmodels.timelines.TimelineComposeViewModel
import me.nathanfallet.extopy.viewmodels.timelines.TimelineViewModel
import me.nathanfallet.ktorx.usecases.api.IGetTokenUseCase
import org.koin.dsl.module

val repositoryModule = module {
    single<IExtopyClient> { ExtopyClient(get(), get()) }
}

val useCaseModule = module {
    // Auth
    single<IFetchTokenUseCase> { FetchTokenUseCase(get(), get()) }
    single<IGetTokenUseCase> { GetTokenUseCase(get()) }
    single<ISetTokenUseCase> { SetTokenUseCase(get()) }
    single<IGetUserIdUseCase> { GetUserIdUseCase(get()) }
    single<ISetUserIdUseCase> { SetUserIdUseCase(get()) }

    // Timelines
    single<IFetchTimelineUseCase> { FetchTimelineUseCase(get()) }

    // Users
    single<IFetchUserUseCase> { FetchUserUseCase(get()) }
}

val viewModelModule = module {
    single { RootViewModel(get(), get()) }
    single { AuthViewModel(get(), get(), get(), get(), get()) }
    single { TimelineViewModel(it[0], get()) }
    single { TimelineComposeViewModel(it[0], it[1], it[2]) }
    single { NotificationsViewModel() }
}

val sharedModule = listOf(
    repositoryModule,
    useCaseModule,
    viewModelModule,
    platformModule()
)
