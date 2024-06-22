package com.extopy.usecases.posts

import com.extopy.models.posts.Post
import dev.kaccelero.models.UUID
import dev.kaccelero.repositories.Pagination
import dev.kaccelero.usecases.IPairSuspendUseCase

interface IFetchPostRepliesUseCase : IPairSuspendUseCase<UUID, Pagination, List<Post>>
