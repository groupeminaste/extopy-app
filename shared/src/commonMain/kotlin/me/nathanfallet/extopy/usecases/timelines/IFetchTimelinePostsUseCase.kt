package me.nathanfallet.extopy.usecases.timelines

import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.usecases.base.ITripleSuspendUseCase

interface IFetchTimelinePostsUseCase : ITripleSuspendUseCase<String, Long, Long, List<Post>>
