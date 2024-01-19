package me.nathanfallet.extopy.features.posts

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
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.launch
import me.nathanfallet.extopy.R
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.ui.components.posts.PostCard
import me.nathanfallet.extopy.viewmodels.posts.PostViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostView(
    id: String,
    viewedBy: User,
    navigate: (String) -> Unit,
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

    LazyColumn(
        modifier
    ) {
        item {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.timeline_post_title))
                },
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            post?.let {
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
