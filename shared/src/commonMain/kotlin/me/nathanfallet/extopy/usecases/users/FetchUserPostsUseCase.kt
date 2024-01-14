package me.nathanfallet.extopy.usecases.users

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post

class FetchUserPostsUseCase(
    private val client: IExtopyClient,
) : IFetchUserPostsUseCase {

    override suspend fun invoke(input1: String, input2: Long, input3: Long): List<Post> {
        return client.users.getPosts(input1, input2, input3)
    }

}
