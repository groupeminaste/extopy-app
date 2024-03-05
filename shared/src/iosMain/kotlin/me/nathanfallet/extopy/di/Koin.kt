package me.nathanfallet.extopy.di

import me.nathanfallet.extopy.viewmodels.auth.AuthViewModel
import me.nathanfallet.extopy.viewmodels.posts.PostViewModel
import me.nathanfallet.extopy.viewmodels.root.RootViewModel
import me.nathanfallet.extopy.viewmodels.timelines.TimelineComposeViewModel
import me.nathanfallet.extopy.viewmodels.timelines.TimelineViewModel
import me.nathanfallet.extopy.viewmodels.users.ProfileViewModel
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
fun Koin.timelineViewModel(id: String): TimelineViewModel = get {
    parametersOf(id)
}

fun Koin.timelineComposeViewModel(body: String, repliedToId: String?, repostOfId: String?): TimelineComposeViewModel =
    get {
        parametersOf(body, repliedToId, repostOfId)
    }

fun Koin.postViewModel(id: String): PostViewModel = get {
    parametersOf(id)
}

fun Koin.profileViewModel(id: String): ProfileViewModel = get {
    parametersOf(id)
}

val Koin.notificationsViewModel: RootViewModel get() = get()
