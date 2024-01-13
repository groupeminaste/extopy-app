package me.nathanfallet.extopy.di

import me.nathanfallet.extopy.client.ExtopyClient
import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.usecases.auth.*
import me.nathanfallet.extopy.usecases.posts.CreatePostUseCase
import me.nathanfallet.extopy.usecases.posts.ICreatePostUseCase
import me.nathanfallet.extopy.usecases.posts.IUpdateLikeInPostUseCase
import me.nathanfallet.extopy.usecases.posts.UpdateLikeInPostUseCase
import me.nathanfallet.extopy.usecases.timelines.FetchTimelineUseCase
import me.nathanfallet.extopy.usecases.timelines.IFetchTimelineUseCase
import me.nathanfallet.extopy.usecases.users.FetchUserUseCase
import me.nathanfallet.extopy.usecases.users.IFetchUserUseCase
import me.nathanfallet.extopy.usecases.users.IUpdateFollowInUserUseCase
import me.nathanfallet.extopy.usecases.users.UpdateFollowInUserUseCase
import me.nathanfallet.extopy.viewmodels.auth.AuthViewModel
import me.nathanfallet.extopy.viewmodels.notifications.NotificationsViewModel
import me.nathanfallet.extopy.viewmodels.root.RootViewModel
import me.nathanfallet.extopy.viewmodels.timelines.TimelineComposeViewModel
import me.nathanfallet.extopy.viewmodels.timelines.TimelineViewModel
import me.nathanfallet.extopy.viewmodels.users.ProfileViewModel
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
    single<IUpdateFollowInUserUseCase> { UpdateFollowInUserUseCase(get(), get()) }

    // Posts
    single<ICreatePostUseCase> { CreatePostUseCase(get()) }
    single<IUpdateLikeInPostUseCase> { UpdateLikeInPostUseCase(get(), get()) }
}

val viewModelModule = module {
    factory { RootViewModel(get(), get(), get()) }
    factory { AuthViewModel(get(), get(), get(), get(), get()) }
    factory { TimelineViewModel(it[0], get(), get(), get()) }
    factory { TimelineComposeViewModel(it[0], it[1], it[2], get()) }
    factory { ProfileViewModel(it[0], get(), get()) }
    factory { NotificationsViewModel() }
}

val sharedModule = listOf(
    repositoryModule,
    useCaseModule,
    viewModelModule,
    platformModule()
)
