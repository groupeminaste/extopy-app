package com.extopy.usecases.posts

import com.extopy.models.posts.Post
import dev.kaccelero.models.UUID
import dev.kaccelero.usecases.ISuspendUseCase

interface IFetchPostUseCase : ISuspendUseCase<UUID, Post?>
