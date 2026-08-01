package com.extopy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.extopy.presentation.navigation.Route
import com.extopy.domain.models.users.User
import com.extopy.presentation.components.base.ExtopyTopBar
import com.extopy.presentation.components.posts.PostCard
import com.extopy.presentation.components.users.UserCard
import com.extopy.presentation.viewmodels.ProfileViewModel
import kotlin.uuid.Uuid
import com.extopy.resources.Res
import com.extopy.resources.timeline_user_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProfileScreen(
    id: Uuid,
    viewedBy: User, // TODO: Manage on view model
    navigate: (Route) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val viewModel = koinViewModel<ProfileViewModel>(
        parameters = { parametersOf(id) }
    )

    LaunchedEffect(id) {
        viewModel.fetchUser()
    }

    val user by viewModel.user.collectAsState()
    val posts by viewModel.posts.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        ExtopyTopBar(
            title = stringResource(Res.string.timeline_user_title),
            onBack = onBack,
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        ) {
            item {
                user?.let {
                    UserCard(
                        user = it,
                        viewedBy = viewedBy,
                        navigate = navigate,
                        onPostsClicked = {},
                        onFollowersClicked = { user ->
                            //navigate("timelines/users/${user.id}/followers")
                        },
                        onFollowingClicked = { user ->
                            //navigate("timelines/users/${user.id}/following")
                        },
                        onEditClicked = {

                        },
                        onFollowClicked = {
                            viewModel.viewModelScope.launch {
                                viewModel.onFollowClicked()
                            }
                        },
                        onSettingsClicked = {

                        },
                        onDirectMessageClicked = {

                        }
                    )
                }
            }
            items(posts ?: listOf()) {
                PostCard(
                    post = it,
                    navigate = navigate,
                    onLikeClicked = { post ->
                        viewModel.viewModelScope.launch {
                            viewModel.onLikeClicked(post)
                        }
                    },
                    onRepostClicked = { post ->
                        navigate(Route.TimelineCompose(repostOfId = post.id.toString()))
                    },
                    onReplyClicked = { post ->
                        navigate(Route.TimelineCompose(repliedToId = post.id.toString()))
                    }
                )
                viewModel.loadMoreIfNeeded(it.id)
            }
        }
    }

}
