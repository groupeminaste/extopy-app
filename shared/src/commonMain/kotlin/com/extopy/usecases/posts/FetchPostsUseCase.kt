package com.extopy.usecases.posts

import com.extopy.client.IExtopyClient
import com.extopy.models.posts.Post
import dev.kaccelero.repositories.Pagination

class FetchPostsUseCase(
    private val client: IExtopyClient,
) : IFetchPostsUseCase {

    override suspend fun invoke(input: Pagination): List<Post> = client.posts.list(input)

}
