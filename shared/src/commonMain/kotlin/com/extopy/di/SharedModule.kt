package com.extopy.di

import com.extopy.client.ExtopyClient
import com.extopy.client.IExtopyClient
import com.extopy.database.Database
import com.extopy.repositories.application.ApplicationRepository
import com.extopy.repositories.application.IApplicationRepository
import com.extopy.repositories.posts.IPostsRepository
import com.extopy.repositories.posts.PostsRepository
import com.extopy.repositories.users.IUsersRepository
import com.extopy.repositories.users.UsersRepository
import com.extopy.usecases.auth.*
import com.extopy.usecases.posts.*
import com.extopy.usecases.timelines.FetchTimelinePostsUseCase
import com.extopy.usecases.timelines.FetchTimelineUseCase
import com.extopy.usecases.timelines.IFetchTimelinePostsUseCase
import com.extopy.usecases.timelines.IFetchTimelineUseCase
import com.extopy.usecases.users.*
import com.extopy.viewmodels.auth.AuthViewModel
import com.extopy.viewmodels.notifications.NotificationsViewModel
import com.extopy.viewmodels.posts.PostViewModel
import com.extopy.viewmodels.root.RootViewModel
import com.extopy.viewmodels.timelines.SearchViewModel
import com.extopy.viewmodels.timelines.TimelineComposeViewModel
import com.extopy.viewmodels.timelines.TimelineViewModel
import com.extopy.viewmodels.users.ProfileViewModel
import dev.kaccelero.commons.auth.IGetTokenUseCase
import dev.kaccelero.commons.auth.ILogoutUseCase
import dev.kaccelero.commons.auth.IRenewTokenUseCase
import org.koin.dsl.module

val databaseModule = module {
    single { Database(get()) }
}

val repositoryModule = module {
    // Remote client
    single<IExtopyClient> { ExtopyClient(get(), get(), get(), get()) }

    // Application repository
    single<IApplicationRepository> { ApplicationRepository(get()) }

    // Local cache
    single<IUsersRepository> { UsersRepository(get()) }
    single<IPostsRepository> { PostsRepository(get(), get()) }
}

val useCaseModule = module {
    // Auth
    single<IFetchTokenUseCase> { FetchTokenUseCase(get(), get(), get(), get()) }
    single<IGetTokenUseCase> { GetTokenUseCase(get()) }
    single<IGetRefreshTokenUseCase> { GetRefreshTokenUseCase(get()) }
    single<ISetTokenUseCase> { SetTokenUseCase(get()) }
    single<IGetUserIdUseCase> { GetUserIdUseCase(get()) }
    single<ISetUserIdUseCase> { SetUserIdUseCase(get()) }
    single<IRenewTokenUseCase> { RenewTokenUseCase(get(), get()) }
    single<ILogoutUseCase> { LogoutUseCase(get(), get()) }
    single<IGetCurrentUserUseCase> { GetCurrentUserUseCase(get(), get()) }

    // Timelines
    single<IFetchTimelineUseCase> { FetchTimelineUseCase(get()) }
    single<IFetchTimelinePostsUseCase> { FetchTimelinePostsUseCase(get()) }

    // Users
    single<IFetchUsersUseCase> { FetchUsersUseCase(get()) }
    single<IFetchUserUseCase> { FetchUserUseCase(get(), get()) }
    single<IUpdateFollowInUserUseCase> { UpdateFollowInUserUseCase(get(), get()) }
    single<IFetchUserPostsUseCase> { FetchUserPostsUseCase(get(), get()) }

    // Posts
    single<ICreatePostUseCase> { CreatePostUseCase(get(), get()) }
    single<IUpdateLikeInPostUseCase> { UpdateLikeInPostUseCase(get(), get()) }
    single<IFetchPostsUseCase> { FetchPostsUseCase(get()) }
    single<IFetchPostUseCase> { FetchPostUseCase(get(), get()) }
    single<IFetchPostRepliesUseCase> { FetchPostRepliesUseCase(get(), get()) }
}

val viewModelModule = module {
    factory { RootViewModel(get(), get()) }
    factory { AuthViewModel(get(), get(), get()) }
    factory { TimelineViewModel(it[0], get(), get(), get(), get()) }
    factory { TimelineComposeViewModel(it[0], it[1], it[2], get()) }
    factory { SearchViewModel(get(), get()) }
    factory { PostViewModel(it[0], get(), get(), get()) }
    factory { ProfileViewModel(it[0], get(), get(), get(), get()) }
    factory { NotificationsViewModel() }
}

val sharedModule = listOf(
    databaseModule,
    repositoryModule,
    useCaseModule,
    viewModelModule,
    platformModule()
)
