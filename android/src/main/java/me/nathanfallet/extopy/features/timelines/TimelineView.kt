package me.nathanfallet.extopy.features.timelines

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
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
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.launch
import me.nathanfallet.extopy.R
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.ui.components.posts.PostCard
import me.nathanfallet.extopy.ui.components.users.UserCard
import me.nathanfallet.extopy.viewmodels.timelines.TimelineViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineView(
    id: String,
    viewedBy: User,
    navigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    val viewModel = koinViewModel<TimelineViewModel>(
        parameters = { parametersOf(id) }
    )

    LaunchedEffect(id) {
        viewModel.fetchTimeline()
    }

    val timeline by viewModel.timeline.collectAsState()
    val search by viewModel.search.collectAsState()

    LazyColumn(
        modifier
    ) {
        item {
            TopAppBar(
                title = {
                    search?.let { search ->
                        TextField(
                            value = search,
                            onValueChange = viewModel::updateSearch,
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.timeline_search_field),
                                    color = Color.LightGray
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    viewModel.viewModelScope.coroutineScope.launch {
                                        viewModel.doSearch()
                                    }
                                }
                            )
                        )
                    } ?: run {
                        Text(stringResource(R.string.timeline_title))
                    }
                },
                actions = {
                    if (search != null) {
                        IconButton(
                            onClick = { viewModel.updateSearch(null) }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                                contentDescription = stringResource(id = R.string.timeline_search_cancel)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { navigate("timeline/compose") }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_create_24),
                                contentDescription = stringResource(id = R.string.timeline_compose_title)
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            viewModel.viewModelScope.coroutineScope.launch {
                                viewModel.doSearch()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = stringResource(id = R.string.timeline_search_title)
                        )
                    }
                }
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        items(timeline?.users ?: listOf()) {
            UserCard(
                user = it,
                viewedBy = viewedBy,
                navigate = navigate,
                onPostsClicked = { user ->
                    navigate.invoke("timeline/user/${user.id}/posts")
                },
                onFollowersClicked = { user ->
                    navigate.invoke("timeline/user/${user.id}/followers")
                },
                onFollowingClicked = { user ->
                    navigate.invoke("timeline/user/${user.id}/following")
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
        items(timeline?.posts ?: listOf()) {
            PostCard(
                post = it,
                navigate = navigate,
                onLikeClicked = { post ->
                    viewModel.viewModelScope.coroutineScope.launch {
                        viewModel.onLikeClicked(post)
                    }
                },
                onRepostClicked = { post ->
                    navigate.invoke("timeline/compose?repostOfId=${post.id}")
                },
                onReplyClicked = { post ->
                    navigate.invoke("timeline/compose?repliedToId=${post.id}")
                }
            )
            // TODO: Load more
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

}
