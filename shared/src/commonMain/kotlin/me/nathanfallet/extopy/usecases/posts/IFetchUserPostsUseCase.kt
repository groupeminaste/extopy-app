package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.usecases.base.ISuspendUseCase

interface IFetchUserPostsUseCase : ISuspendUseCase<String, List<Post>?>
