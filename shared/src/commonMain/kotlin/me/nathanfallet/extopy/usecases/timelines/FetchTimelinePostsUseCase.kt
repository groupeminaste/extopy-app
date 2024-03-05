package me.nathanfallet.extopy.usecases.timelines

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.usecases.pagination.Pagination

class FetchTimelinePostsUseCase(
    private val client: IExtopyClient,
) : IFetchTimelinePostsUseCase {

    override suspend fun invoke(input1: String, input2: Pagination): List<Post> =
        client.timelines.getPosts(input1, input2)

}
