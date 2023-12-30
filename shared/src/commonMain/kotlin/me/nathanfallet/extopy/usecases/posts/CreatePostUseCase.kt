package me.nathanfallet.extopy.usecases.posts

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.models.posts.PostPayload

class CreatePostUseCase(
    private val client: IExtopyClient,
) : ICreatePostUseCase {

    override suspend fun invoke(input: PostPayload): Post? {
        return client.posts.create(input)
    }

}
