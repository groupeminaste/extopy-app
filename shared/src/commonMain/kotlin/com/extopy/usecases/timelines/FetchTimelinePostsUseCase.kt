package com.extopy.usecases.timelines

import com.extopy.client.IExtopyClient
import com.extopy.models.posts.Post
import dev.kaccelero.models.UUID
import dev.kaccelero.repositories.Pagination

class FetchTimelinePostsUseCase(
    private val client: IExtopyClient,
) : IFetchTimelinePostsUseCase {

    override suspend fun invoke(input1: UUID, input2: Pagination): List<Post> =
        client.timelines.getPosts(input1, input2)

}
