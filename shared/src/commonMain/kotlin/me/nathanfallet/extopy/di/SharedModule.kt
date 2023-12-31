package me.nathanfallet.extopy.di

import me.nathanfallet.extopy.client.ExtopyClient
import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.usecases.auth.*
import me.nathanfallet.extopy.usecases.posts.IUploadPostUseCase
import me.nathanfallet.extopy.usecases.posts.UploadPostUseCase
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

    // Posts
    single<IUploadPostUseCase> { UploadPostUseCase(get()) }
}

val viewModelModule = module {
    factory { RootViewModel(get(), get()) }
    factory { AuthViewModel(get(), get(), get(), get(), get()) }
    factory { TimelineViewModel(it[0], get()) }
    factory { TimelineComposeViewModel(it[0], it[1], it[2], get()) }
    factory { NotificationsViewModel() }
}

val sharedModule = listOf(
    repositoryModule,
    useCaseModule,
    viewModelModule,
    platformModule()
)
