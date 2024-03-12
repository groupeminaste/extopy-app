package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.usecases.pagination.Pagination

class FetchPostsUseCase(
    private val client: IExtopyClient,
) : IFetchPostsUseCase {

    override suspend fun invoke(input: Pagination): List<Post> = client.posts.list(input)

}
