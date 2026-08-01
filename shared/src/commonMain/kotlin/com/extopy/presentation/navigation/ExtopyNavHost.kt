package com.extopy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.NavUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.extopy.domain.models.users.User
import com.extopy.presentation.screens.AuthScreen
import com.extopy.presentation.screens.MainScreen
import com.extopy.presentation.screens.PostScreen
import com.extopy.presentation.screens.ProfileScreen
import com.extopy.presentation.screens.TimelineComposeScreen
import com.extopy.presentation.viewmodels.RootViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.Uuid

/**
 * The whole navigation graph.
 *
 * Which of the two graphs is mounted depends on whether anyone is signed in: no screen makes sense
 * in both, and swapping the graph rather than gating each destination means a signed-out user
 * cannot end up somewhere that needs a session.
 *
 * Inside the signed-in graph there are only four destinations, not seven: the tabs live inside
 * [MainScreen] as state. Everything that *is* a destination here is something you open on top of a
 * tab and come back from.
 */
@Composable
fun ExtopyNavHost() {

    val viewModel = koinViewModel<RootViewModel>()

    val user by viewModel.user.collectAsState()

    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.fetchUser()
    }

    DisposableEffect(Unit) {
        // Sets up the listener to call `NavController.navigate()`
        // for the composable that has a matching `navDeepLink` listed
        ExternalUriHandler.listener = { uri ->
            navController.navigate(NavUri(uri))
        }
        // Removes the listener when the composable is no longer active
        onDispose {
            ExternalUriHandler.listener = null
        }
    }

    user?.let {
        SignedInNavigation(viewedBy = it, navController = navController)
    } ?: AuthNavigation(
        navController = navController,
        onUserLogged = {
            viewModel.viewModelScope.launch {
                viewModel.fetchUser()
            }
        },
    )

}

@Composable
fun SignedInNavigation(
    viewedBy: User,
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Route.Main,
    ) {
        composable<Route.Main> {
            MainScreen(
                viewedBy = viewedBy,
                navigate = navController::navigate,
            )
        }
        composable<Route.TimelineCompose> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.TimelineCompose>()
            TimelineComposeScreen(
                onPostComposed = navController::navigateUp,
                onBack = navController::navigateUp,
                repliedToId = route.repliedToId.toUuidOrNull(),
                repostOfId = route.repostOfId.toUuidOrNull(),
            )
        }
        composable<Route.TimelineUser> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.TimelineUser>()
            ProfileScreen(
                id = Uuid.parse(route.userId),
                viewedBy = viewedBy,
                navigate = navController::navigate,
                onBack = navController::navigateUp,
            )
        }
        composable<Route.TimelinePost> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.TimelinePost>()
            PostScreen(
                id = Uuid.parse(route.postId),
                navigate = navController::navigate,
                onBack = navController::navigateUp,
            )
        }
    }

}

@Composable
fun AuthNavigation(
    navController: NavHostController,
    onUserLogged: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = Route.Auth,
    ) {
        composable<Route.Auth> {
            AuthScreen(onUserLogged = onUserLogged)
        }
        composable<Route.AuthWithCode>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "extopy://auth?code={code}"
                }
            )
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<Route.AuthWithCode>()
            AuthScreen(
                onUserLogged = onUserLogged,
                code = route.code,
            )
        }
    }

}

/** Reads an optional id back out of a route, where an absent one travels as the text `"null"`. */
private fun String.toUuidOrNull(): Uuid? = takeIf { it != "null" }?.let { Uuid.parse(it) }
