package com.extopy.features.timelines

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.extopy.R
import com.extopy.models.users.User
import com.extopy.ui.components.posts.PostCard
import com.extopy.ui.components.users.UserCard
import com.extopy.viewmodels.timelines.SearchViewModel
import com.extopy.viewmodels.timelines.TimelineViewModel
import com.rickclephas.kmp.observableviewmodel.coroutineScope
import dev.kaccelero.models.UUID
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineView(
    id: UUID,
    viewedBy: User,
    navigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    val viewModel = koinViewModel<TimelineViewModel>(
        parameters = { parametersOf(id) }
    )
    val searchViewModel = koinViewModel<SearchViewModel>()

    LaunchedEffect(id) {
        viewModel.fetchTimeline()
    }

    val timeline by viewModel.timeline.collectAsState()
    val users by viewModel.users.collectAsState()
    val posts by viewModel.posts.collectAsState()

    val search by searchViewModel.search.collectAsState()
    val searchUsers by searchViewModel.users.collectAsState()
    val searchPosts by searchViewModel.posts.collectAsState()

    LazyColumn(
        modifier
    ) {
        item {
            TopAppBar(
                title = { Text(stringResource(R.string.timeline_title)) },
                actions = {
                    IconButton(
                        onClick = { navigate("timelines/compose") }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_create_24),
                            contentDescription = stringResource(id = R.string.timeline_compose_title)
                        )
                    }
                }
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            TextField(
                value = search,
                onValueChange = searchViewModel::updateSearch,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.timeline_search_field),
                        color = Color.LightGray
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
        items(searchUsers?.takeIf { it.isNotEmpty() } ?: users ?: listOf()) {
            UserCard(
                user = it,
                viewedBy = viewedBy,
                navigate = navigate,
                onPostsClicked = { user ->
                    navigate.invoke("timelines/users/${user.id}/posts")
                },
                onFollowersClicked = { user ->
                    navigate.invoke("timelines/users/${user.id}/followers")
                },
                onFollowingClicked = { user ->
                    navigate.invoke("timelines/users/${user.id}/following")
                },
                onEditClicked = {

                },
                onFollowClicked = { user ->
                    viewModel.viewModelScope.coroutineScope.launch {
                        viewModel.onFollowClicked(user)
                    }
                },
                onSettingsClicked = {

                },
                onDirectMessageClicked = {

                }
            )
        }
        items(searchPosts?.takeIf { it.isNotEmpty() } ?: posts ?: listOf()) {
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
