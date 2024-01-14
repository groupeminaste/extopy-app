package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post

class FetchPostUseCase(
    private val client: IExtopyClient,
) : IFetchPostUseCase {

    override suspend fun invoke(input: String): Post? {
        return client.posts.get(input)
    }

}
