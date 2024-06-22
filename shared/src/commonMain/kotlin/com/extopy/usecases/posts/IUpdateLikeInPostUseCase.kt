package com.extopy.usecases.posts

import com.extopy.models.posts.Post
import dev.kaccelero.usecases.ISuspendUseCase

interface IUpdateLikeInPostUseCase : ISuspendUseCase<Post, Post?>
