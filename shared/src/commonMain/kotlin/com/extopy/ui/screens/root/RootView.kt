package com.extopy.ui.screens.root

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.extopy.models.navigation.ExternalUriHandler
import com.extopy.models.navigation.NavigationItem
import com.extopy.models.navigation.Route
import com.extopy.models.timelines.Timeline
import com.extopy.models.users.User
import com.extopy.ui.screens.auth.AuthView
import com.extopy.ui.screens.notifications.NotificationsView
import com.extopy.ui.screens.posts.PostView
import com.extopy.ui.screens.timelines.TimelineComposeView
import com.extopy.ui.screens.timelines.TimelineView
import com.extopy.ui.screens.users.ProfileView
import com.extopy.viewmodels.root.RootViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import dev.kaccelero.models.UUID
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RootView() {

    val viewModel = koinViewModel<RootViewModel>()

    val user by viewModel.user.collectAsState()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

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
            NavigationBar {
                val currentRoute = navBackStackEntry?.destination?.route
                NavigationItem.entries.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painterResource(item.icon),
                                contentDescription = stringResource(item.title)
                            )
                        },
                        label = { Text(text = stringResource(item.title)) },
                        alwaysShowLabel = true,
                        selected = currentRoute?.lowercase()?.startsWith(item.name.replace("_", "").lowercase())
                            ?: false,
                        onClick = {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
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
                    viewModel.viewModelScope.coroutineScope.launch {
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
            TimelineView(
                id = Timeline.defaultId,
                viewedBy = viewedBy,
                navigate = navController::navigate,
                modifier = Modifier.padding(padding)
            )
        }
        composable<Route.TimelineCompose> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.TimelineCompose>()
            TimelineComposeView(
                onPostComposed = navController::navigateUp,
                repliedToId = route.repliedToId.takeIf { it != "null" }?.let(::UUID),
                repostOfId = route.repostOfId.takeIf { it != "null" }?.let(::UUID),
                modifier = Modifier.padding(padding),
            )
        }
        composable<Route.TimelineUser> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.TimelineUser>()
            ProfileView(
                id = route.userId.let(::UUID),
                viewedBy = viewedBy,
                navigate = navController::navigate,
                modifier = Modifier.padding(padding)
            )
        }
        composable<Route.TimelinePost> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.TimelinePost>()
            PostView(
                id = route.postId.let(::UUID),
                navigate = navController::navigate,
                modifier = Modifier.padding(padding)
            )
        }
        composable<Route.DirectMessages> {

        }
        composable<Route.Notifications> {
            NotificationsView(modifier = Modifier.padding(padding))
        }
        composable<Route.Settings> {
            /*
            SettingsView(
                modifier = Modifier.padding(padding),
                navigate = navController::navigate
            )
            */
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
            AuthView(
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
            AuthView(
                onUserLogged = onUserLogged,
                modifier = Modifier.padding(padding),
                code = route.code
            )
        }
    }

}
