package com.extopy.usecases.posts

import com.extopy.client.IExtopyClient
import com.extopy.models.posts.Post
import com.extopy.repositories.posts.IPostsRepository
import dev.kaccelero.models.UUID
import dev.kaccelero.repositories.Pagination
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus

class FetchPostRepliesUseCase(
    private val client: IExtopyClient,
    private val postsRepository: IPostsRepository,
) : IFetchPostRepliesUseCase {

    override suspend fun invoke(input1: UUID, input2: Pagination): List<Post> =
        client.posts.getReplies(input1, input2).onEach {
            postsRepository.save(
                it,
                Clock.System.now().plus(60, DateTimeUnit.SECOND, TimeZone.currentSystemDefault())
            )
        }

}
