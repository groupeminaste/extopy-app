package com.extopy.ui.screens.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.extopy.models.navigation.Route
import com.extopy.models.users.User
import com.extopy.ui.components.posts.PostCard
import com.extopy.ui.components.users.UserCard
import com.extopy.viewmodels.users.ProfileViewModel
import dev.kaccelero.models.UUID
import extopy_app.shared.generated.resources.Res
import extopy_app.shared.generated.resources.timeline_user_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    id: UUID,
    viewedBy: User, // TODO: Manage on view model
    navigate: (Route) -> Unit,
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
        CenterAlignedTopAppBar(
            title = {
                Text(stringResource(Res.string.timeline_user_title))
            },
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, (16 + 88).dp),
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
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

}
