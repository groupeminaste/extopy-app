package com.extopy.ui.screens.posts

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
import com.extopy.ui.components.posts.PostCard
import com.extopy.viewmodels.posts.PostViewModel
import dev.kaccelero.models.UUID
import extopy_app.shared.generated.resources.Res
import extopy_app.shared.generated.resources.timeline_post_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostView(
    id: UUID,
    navigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {

    val viewModel = koinViewModel<PostViewModel>(
        parameters = { parametersOf(id) }
    )

    LaunchedEffect(id) {
        viewModel.fetchPost()
    }

    val post by viewModel.post.collectAsState()
    val posts by viewModel.posts.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(stringResource(Res.string.timeline_post_title))
            },
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, (16 + 88).dp),
        ) {
            item {
                post?.let {
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
