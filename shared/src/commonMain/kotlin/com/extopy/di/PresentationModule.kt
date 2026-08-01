package com.extopy.di

import com.extopy.presentation.viewmodels.AuthViewModel
import com.extopy.presentation.viewmodels.NotificationsViewModel
import com.extopy.presentation.viewmodels.PostViewModel
import com.extopy.presentation.viewmodels.ProfileViewModel
import com.extopy.presentation.viewmodels.RootViewModel
import com.extopy.presentation.viewmodels.SearchViewModel
import com.extopy.presentation.viewmodels.SettingsViewModel
import com.extopy.presentation.viewmodels.TimelineComposeViewModel
import com.extopy.presentation.viewmodels.TimelineViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import kotlin.uuid.Uuid

/**
 * The view models.
 *
 * The ones taking an id — a post, a profile, a timeline — are built with it rather than told about
 * it afterwards, so a screen never exists in a state where it does not yet know what it is showing.
 */
val presentationModule = module {
    viewModelOf(::RootViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::NotificationsViewModel)
    viewModelOf(::SettingsViewModel)
    viewModel { (id: Uuid) -> TimelineViewModel(id, get(), get(), get()) }
    viewModel { (id: Uuid) -> PostViewModel(id, get(), get()) }
    viewModel { (id: Uuid) -> ProfileViewModel(id, get(), get(), get()) }
    viewModel { (body: String, repliedToId: Uuid?, repostOfId: Uuid?) ->
        TimelineComposeViewModel(body, repliedToId, repostOfId, get())
    }
}
