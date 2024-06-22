package com.extopy.usecases.posts

import com.extopy.client.IExtopyClient
import com.extopy.models.posts.Post
import com.extopy.models.posts.PostPayload
import com.extopy.repositories.posts.IPostsRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus

class CreatePostUseCase(
    private val client: IExtopyClient,
    private val postsRepository: IPostsRepository,
) : ICreatePostUseCase {

    override suspend fun invoke(input: PostPayload): Post? =
        client.posts.create(input)?.also {
            postsRepository.save(
                it,
                Clock.System.now().plus(60, DateTimeUnit.SECOND, TimeZone.currentSystemDefault())
            )
        }

}
