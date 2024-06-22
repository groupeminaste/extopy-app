package com.extopy.usecases.timelines

import com.extopy.models.posts.Post
import dev.kaccelero.models.UUID
import dev.kaccelero.repositories.Pagination
import dev.kaccelero.usecases.IPairSuspendUseCase

interface IFetchTimelinePostsUseCase : IPairSuspendUseCase<UUID, Pagination, List<Post>>
