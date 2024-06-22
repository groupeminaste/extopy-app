package com.extopy.usecases.posts

import com.extopy.models.posts.Post
import dev.kaccelero.repositories.Pagination
import dev.kaccelero.usecases.ISuspendUseCase

interface IFetchPostsUseCase : ISuspendUseCase<Pagination, List<Post>>
