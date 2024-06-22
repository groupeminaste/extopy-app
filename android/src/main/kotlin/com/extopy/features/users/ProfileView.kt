package com.extopy.features.users

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.extopy.R
import com.extopy.models.users.User
import com.extopy.ui.components.posts.PostCard
import com.extopy.ui.components.users.UserCard
import com.extopy.viewmodels.users.ProfileViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import dev.kaccelero.models.UUID
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    id: UUID,
    viewedBy: User, // TODO: Manage on view model
    navigate: (String) -> Unit,
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

    LazyColumn(
        modifier
    ) {
        item {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.timeline_user_title))
                },
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            user?.let {
                UserCard(
                    user = it,
                    viewedBy = viewedBy,
                    navigate = navigate,
                    onPostsClicked = {},
                    onFollowersClicked = { user ->
                        navigate.invoke("timelines/users/${user.id}/followers")
                    },
                    onFollowingClicked = { user ->
                        navigate.invoke("timelines/users/${user.id}/following")
                    },
                    onEditClicked = {

                    },
                    onFollowClicked = {
                        viewModel.viewModelScope.coroutineScope.launch {
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
                    viewModel.viewModelScope.coroutineScope.launch {
                        viewModel.onLikeClicked(post)
                    }
                },
                onRepostClicked = { post ->
                    navigate.invoke("timelines/compose?repostOfId=${post.id}")
                },
                onReplyClicked = { post ->
                    navigate.invoke("timelines/compose?repliedToId=${post.id}")
                }
            )
            viewModel.loadMoreIfNeeded(it.id)
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

}
