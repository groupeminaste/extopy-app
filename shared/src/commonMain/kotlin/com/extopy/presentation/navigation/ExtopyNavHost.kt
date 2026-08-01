package com.extopy.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.NavUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.extopy.domain.models.timelines.Timeline
import com.extopy.domain.models.users.User
import com.extopy.presentation.components.base.CustomNavigationBar
import com.extopy.presentation.screens.AuthScreen
import com.extopy.presentation.screens.NotificationsScreen
import com.extopy.presentation.screens.PostScreen
import com.extopy.presentation.screens.ProfileScreen
import com.extopy.presentation.screens.SettingsScreen
import com.extopy.presentation.screens.TimelineComposeScreen
import com.extopy.presentation.screens.TimelineScreen
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

    Scaffold(
        bottomBar = {
            if (user == null) return@Scaffold
            CustomNavigationBar(navController)
        }
    ) { padding ->
        user?.let {
            TabNavigation(
                viewedBy = it,
                navController = navController,
                padding = padding
            )
        } ?: run {
            AuthNavigation(
                navController = navController,
                padding = padding,
                onUserLogged = {
                    viewModel.viewModelScope.launch {
                        viewModel.fetchUser()
                    }
                }
            )
        }
    }

}

@Composable
fun TabNavigation(
    viewedBy: User,
    navController: NavHostController,
    padding: PaddingValues,
) {

    NavHost(
        navController = navController,
        startDestination = Route.Timelines
    ) {
        composable<Route.Timelines> {
            TimelineScreen(
                id = Timeline.defaultId,
                viewedBy = viewedBy,
                navigate = navController::navigate,
                modifier = Modifier.padding(padding)
            )
        }
        composable<Route.TimelineCompose> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.TimelineCompose>()
            TimelineComposeScreen(
                onPostComposed = navController::navigateUp,
                repliedToId = route.repliedToId.toUuidOrNull(),
                repostOfId = route.repostOfId.toUuidOrNull(),
                modifier = Modifier.padding(padding),
            )
        }
        composable<Route.TimelineUser> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.TimelineUser>()
            ProfileScreen(
                id = Uuid.parse(route.userId),
                viewedBy = viewedBy,
                navigate = navController::navigate,
                modifier = Modifier.padding(padding)
            )
        }
        composable<Route.TimelinePost> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.TimelinePost>()
            PostScreen(
                id = Uuid.parse(route.postId),
                navigate = navController::navigate,
                modifier = Modifier.padding(padding)
            )
        }
        composable<Route.DirectMessages> {

        }
        composable<Route.Notifications> {
            NotificationsScreen(modifier = Modifier.padding(padding))
        }
        composable<Route.Settings> {
            SettingsScreen(
                modifier = Modifier.padding(padding),
                navigate = navController::navigate
            )
        }
    }

}

@Composable
fun AuthNavigation(
    navController: NavHostController,
    padding: PaddingValues,
    onUserLogged: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = Route.Auth
    ) {
        composable<Route.Auth> {
            AuthScreen(
                onUserLogged = onUserLogged,
                modifier = Modifier.padding(padding)
            )
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
                modifier = Modifier.padding(padding),
                code = route.code
            )
        }
    }

}

/** Reads an optional id back out of a route, where an absent one travels as the text `"null"`. */
private fun String.toUuidOrNull(): Uuid? = takeIf { it != "null" }?.let { Uuid.parse(it) }
