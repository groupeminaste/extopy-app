package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.usecases.base.ITripleSuspendUseCase

interface IFetchUserPostsUseCase : ITripleSuspendUseCase<String, Long, Long, List<Post>>
