package com.extopy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.adamglin.PhosphorIcons
import com.adamglin.phosphoricons.Regular
import com.adamglin.phosphoricons.regular.NotePencil
import com.extopy.domain.models.users.User
import com.extopy.presentation.components.base.EmptyState
import com.extopy.presentation.components.base.ExtopySearchField
import com.extopy.presentation.components.posts.PostCard
import com.extopy.presentation.components.users.UserCard
import com.extopy.presentation.navigation.Route
import com.extopy.presentation.theme.PillShape
import com.extopy.presentation.viewmodels.SearchViewModel
import com.extopy.presentation.viewmodels.TimelineViewModel
import com.extopy.resources.Res
import com.extopy.resources.timeline_compose_title
import com.extopy.resources.timeline_empty_body
import com.extopy.resources.timeline_empty_title
import com.extopy.resources.timeline_greeting
import com.extopy.resources.timeline_search_field
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.uuid.Uuid

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

    val users by viewModel.users.collectAsState()
    val posts by viewModel.posts.collectAsState()

    val search by searchViewModel.search.collectAsState()
    val searchUsers by searchViewModel.users.collectAsState()
    val searchPosts by searchViewModel.posts.collectAsState()

    val shownUsers = searchUsers?.takeIf { it.isNotEmpty() } ?: users ?: listOf()
    val shownPosts = searchPosts?.takeIf { it.isNotEmpty() } ?: posts ?: listOf()

    Column(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 12.dp),
        ) {
            Text(
                text = stringResource(Res.string.timeline_greeting, viewedBy.displayName),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ExtopySearchField(
                    value = search,
                    onValueChange = searchViewModel::updateSearch,
                    placeholder = stringResource(Res.string.timeline_search_field),
                    modifier = Modifier.weight(1f),
                )
                // The compose action is the one thing this screen is *for*, so it gets the accent.
                Icon(
                    imageVector = PhosphorIcons.Regular.NotePencil,
                    contentDescription = stringResource(Res.string.timeline_compose_title),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clip(PillShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { navigate(Route.TimelineCompose()) }
                        .padding(12.dp)
                        .size(20.dp),
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        ) {
            items(shownUsers) {
                UserCard(
                    user = it,
                    viewedBy = viewedBy,
                    navigate = navigate,
                    onPostsClicked = { user ->
                        navigate(Route.TimelineUser(user.id.toString()))
                    },
                    onFollowersClicked = { },
                    onFollowingClicked = { },
                    onEditClicked = { },
                    onFollowClicked = { user ->
                        viewModel.viewModelScope.launch {
                            viewModel.onFollowClicked(user)
                        }
                    },
                    onSettingsClicked = { },
                    onDirectMessageClicked = { },
                )
            }
            items(shownPosts) {
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
                    },
                )
                viewModel.loadMoreIfNeeded(it.id)
            }
            if (shownUsers.isEmpty() && shownPosts.isEmpty()) {
                item {
                    EmptyState(
                        title = stringResource(Res.string.timeline_empty_title),
                        body = stringResource(Res.string.timeline_empty_body),
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

}
