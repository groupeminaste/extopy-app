package com.extopy.di

import com.extopy.viewmodels.auth.AuthViewModel
import com.extopy.viewmodels.posts.PostViewModel
import com.extopy.viewmodels.root.RootViewModel
import com.extopy.viewmodels.timelines.SearchViewModel
import com.extopy.viewmodels.timelines.TimelineComposeViewModel
import com.extopy.viewmodels.timelines.TimelineViewModel
import com.extopy.viewmodels.users.ProfileViewModel
import dev.kaccelero.models.UUID
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf

fun KoinApplication.Companion.start(): KoinApplication = startKoin {
    modules(sharedModule)
}

// MARK: - View models (we should not call any other class from iOS directly, only view models)

val Koin.rootViewModel: RootViewModel get() = get()
val Koin.authViewModel: AuthViewModel get() = get()
fun Koin.timelineViewModel(id: UUID): TimelineViewModel = get {
    parametersOf(id)
}

fun Koin.timelineComposeViewModel(body: String, repliedToId: UUID?, repostOfId: UUID?): TimelineComposeViewModel =
    get {
        parametersOf(body, repliedToId, repostOfId)
    }

val Koin.searchViewModel: SearchViewModel get() = get()

fun Koin.postViewModel(id: UUID): PostViewModel = get {
    parametersOf(id)
}

fun Koin.profileViewModel(id: UUID): ProfileViewModel = get {
    parametersOf(id)
}

val Koin.notificationsViewModel: RootViewModel get() = get()
