package me.nathanfallet.extopy.usecases.timelines

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post

class FetchTimelinePostsUseCase(
    private val client: IExtopyClient,
) : IFetchTimelinePostsUseCase {

    override suspend fun invoke(input1: String, input2: Long, input3: Long): List<Post> {
        return client.timelines.getPosts(input1, input2, input3)
    }

}
