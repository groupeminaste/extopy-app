package com.extopy.usecases.posts

import com.extopy.client.IExtopyClient
import com.extopy.models.posts.Post
import com.extopy.repositories.posts.IPostsRepository
import dev.kaccelero.models.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus

class FetchPostUseCase(
    private val client: IExtopyClient,
    private val postsRepository: IPostsRepository,
) : IFetchPostUseCase {

    override suspend fun invoke(input: UUID): Post? =
        postsRepository.get(input) ?: client.posts.get(input)?.also {
            postsRepository.save(
                it,
                Clock.System.now().plus(60, DateTimeUnit.SECOND, TimeZone.currentSystemDefault())
            )
        }

}
