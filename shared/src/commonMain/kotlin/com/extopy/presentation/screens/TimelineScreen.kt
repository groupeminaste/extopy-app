package com.extopy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.extopy.presentation.navigation.Route
import com.extopy.domain.models.users.User
import com.extopy.presentation.components.posts.PostCard
import com.extopy.presentation.components.users.UserCard
import com.extopy.presentation.viewmodels.SearchViewModel
import com.extopy.presentation.viewmodels.TimelineViewModel
import kotlin.uuid.Uuid
import com.extopy.resources.Res
import com.extopy.resources.ic_baseline_create_24
import com.extopy.resources.timeline_compose_title
import com.extopy.resources.timeline_search_field
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    id: Uuid,
    viewedBy: User,
    navigate: (Route) -> Unit,
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

    Column(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = searchViewModel::updateSearch,
                placeholder = {
                    Text(
                        text = stringResource(Res.string.timeline_search_field),
                        color = Color.LightGray
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(1f)
            )
            Icon(
                painter = painterResource(Res.drawable.ic_baseline_create_24),
                contentDescription = stringResource(Res.string.timeline_compose_title)
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, (16 + 88).dp),
        ) {
            items(searchUsers?.takeIf { it.isNotEmpty() } ?: users ?: listOf()) {
                UserCard(
                    user = it,
                    viewedBy = viewedBy,
                    navigate = navigate,
                    onPostsClicked = { user ->
                        navigate(Route.TimelineUser(user.id.toString()))
                    },
                    onFollowersClicked = { user ->
                        //navigate("timelines/users/${user.id}/followers")
                    },
                    onFollowingClicked = { user ->
                        //navigate("timelines/users/${user.id}/following")
                    },
                    onEditClicked = {

                    },
                    onFollowClicked = { user ->
                        viewModel.viewModelScope.launch {
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
