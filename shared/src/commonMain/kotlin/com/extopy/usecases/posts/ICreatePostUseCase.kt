package com.extopy.usecases.posts

import com.extopy.models.posts.Post
import com.extopy.models.posts.PostPayload
import dev.kaccelero.usecases.ISuspendUseCase

interface ICreatePostUseCase : ISuspendUseCase<PostPayload, Post?>
