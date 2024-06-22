package com.extopy.usecases.users

import com.extopy.models.posts.Post
import dev.kaccelero.models.UUID
import dev.kaccelero.repositories.Pagination
import dev.kaccelero.usecases.IPairSuspendUseCase

interface IFetchUserPostsUseCase : IPairSuspendUseCase<UUID, Pagination, List<Post>>
