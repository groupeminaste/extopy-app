package com.extopy.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.extopy.domain.models.posts.Post
import com.extopy.presentation.components.base.ExtopyTopBar
import com.extopy.presentation.components.posts.PostCard
import com.extopy.presentation.navigation.Route
import com.extopy.presentation.viewmodels.PostViewModel
import com.extopy.resources.Res
import com.extopy.resources.timeline_post_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.uuid.Uuid

@Composable
fun PostScreen(
    id: Uuid,
    navigate: (Route) -> Unit,
    onBack: () -> Unit,
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

    val onLike: (Post) -> Unit = { clicked ->
        viewModel.viewModelScope.launch { viewModel.onLikeClicked(clicked) }
    }

    Column(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        ExtopyTopBar(
            title = stringResource(Res.string.timeline_post_title),
            onBack = onBack,
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        ) {
            item {
                post?.let {
                    PostCard(
                        post = it,
                        navigate = navigate,
                        onLikeClicked = onLike,
                        onRepostClicked = { clicked ->
                            navigate(Route.TimelineCompose(repostOfId = clicked.id.toString()))
                        },
                        onReplyClicked = { clicked ->
                            navigate(Route.TimelineCompose(repliedToId = clicked.id.toString()))
                        },
                    )
                }
            }
            items(posts ?: listOf()) {
                PostCard(
                    post = it,
                    navigate = navigate,
                    onLikeClicked = onLike,
                    onRepostClicked = { clicked ->
                        navigate(Route.TimelineCompose(repostOfId = clicked.id.toString()))
                    },
                    onReplyClicked = { clicked ->
                        navigate(Route.TimelineCompose(repliedToId = clicked.id.toString()))
                    },
                )
                viewModel.loadMoreIfNeeded(it.id)
            }
        }
    }

}
