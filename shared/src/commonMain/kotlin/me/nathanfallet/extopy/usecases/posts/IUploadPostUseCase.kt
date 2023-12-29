package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.models.posts.PostPayload
import me.nathanfallet.usecases.base.ISuspendUseCase

interface IUploadPostUseCase : ISuspendUseCase<PostPayload, Post?>
