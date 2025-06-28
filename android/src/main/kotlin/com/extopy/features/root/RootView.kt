package com.extopy.features.root

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.extopy.R
import com.extopy.features.settings.SettingsView
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun RootView() {

    val viewModel = koinViewModel<RootViewModel>()

    val user by viewModel.user.collectAsState()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUser()
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
                                painterResource(id = item.icon),
                                contentDescription = stringResource(item.title)
                            )
                        },
                        label = { Text(text = stringResource(item.title)) },
                        alwaysShowLabel = true,
                        selected = currentRoute?.startsWith(item.route) ?: false,
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
        startDestination = "timelines"
    ) {
        composable("timelines") {
            TimelineView(
                id = Timeline.defaultId,
                viewedBy = viewedBy,
                navigate = navController::navigate,
                modifier = Modifier.padding(padding)
            )
        }
        composable(
            "timelines/compose?repliedToId={repliedToId}&repostOfId={repostOfId}",
            arguments = listOf(
                navArgument("repliedToId") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("repostOfId") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            TimelineComposeView(
                modifier = Modifier.padding(padding),
                onPostComposed = navController::navigateUp,
                repliedToId = backStackEntry.arguments?.getString("repliedToId")?.let(::UUID),
                repostOfId = backStackEntry.arguments?.getString("repostOfId")?.let(::UUID)
            )
        }
        composable("timelines/users/{userId}") { backStackEntry ->
            ProfileView(
                id = backStackEntry.arguments?.getString("userId")?.let(::UUID)!!,
                viewedBy = viewedBy,
                navigate = navController::navigate,
                modifier = Modifier.padding(padding)
            )
        }
        composable("timelines/posts/{postId}") { backStackEntry ->
            PostView(
                id = backStackEntry.arguments?.getString("postId")?.let(::UUID)!!,
                navigate = navController::navigate,
                modifier = Modifier.padding(padding)
            )
        }
        composable("direct_message") {

        }
        composable("notifications") {
            NotificationsView(modifier = Modifier.padding(padding))
        }
        composable("settings") {
            SettingsView(
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
        startDestination = "auth"
    ) {
        composable("auth") {
            AuthView(
                onUserLogged = onUserLogged,
                modifier = Modifier.padding(padding)
            )
        }
        composable(
            "auth/code?code={code}",
            arguments = listOf(
                navArgument("code") { type = NavType.StringType }
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "extopy://auth?code={code}"
                }
            )
        ) { backStackEntry ->
            AuthView(
                onUserLogged = onUserLogged,
                modifier = Modifier.padding(padding),
                code = backStackEntry.arguments?.getString("code")
            )
        }
    }
}

enum class NavigationItem(
    val route: String,
    val icon: Int,
    val title: Int,
) {

    TIMELINE("timelines", R.drawable.ic_baseline_menu_24, R.string.timeline_title),
    DIRECT_MESSAGE(
        "direct_message",
        R.drawable.ic_baseline_message_24,
        R.string.direct_message_title
    ),
    NOTIFICATIONS(
        "notifications",
        R.drawable.ic_baseline_notifications_24,
        R.string.notifications_title
    ),
    SETTINGS("settings", R.drawable.ic_baseline_settings_24, R.string.settings_title)

}
