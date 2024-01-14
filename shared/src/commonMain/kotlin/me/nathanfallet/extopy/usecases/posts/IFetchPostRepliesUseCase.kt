package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.usecases.base.ITripleSuspendUseCase

interface IFetchPostRepliesUseCase : ITripleSuspendUseCase<String, Long, Long, List<Post>>
