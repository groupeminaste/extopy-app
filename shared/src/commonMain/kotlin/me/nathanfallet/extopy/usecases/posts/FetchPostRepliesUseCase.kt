package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post

class FetchPostRepliesUseCase(
    private val client: IExtopyClient,
) : IFetchPostRepliesUseCase {

    override suspend fun invoke(input1: String, input2: Long, input3: Long): List<Post> {
        return client.posts.getReplies(input1, input2, input3)
    }

}
