package com.extopy.di

import com.extopy.domain.usecases.auth.AuthenticateUseCase
import com.extopy.domain.usecases.auth.GetCurrentUserUseCase
import com.extopy.domain.usecases.auth.LogoutUseCase
import com.extopy.domain.usecases.posts.ToggleLikeUseCase
import com.extopy.domain.usecases.users.ToggleFollowUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * The use cases.
 *
 * Only the operations that decide something are here — signing in, signing out, flipping a like or
 * a follow. Plain reads go straight through the repositories, which the view models take directly.
 */
val domainModule = module {
    singleOf(::AuthenticateUseCase)
    singleOf(::GetCurrentUserUseCase)
    singleOf(::LogoutUseCase)
    singleOf(::ToggleLikeUseCase)
    singleOf(::ToggleFollowUseCase)
}
