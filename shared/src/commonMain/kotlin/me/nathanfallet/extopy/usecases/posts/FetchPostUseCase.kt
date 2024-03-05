package me.nathanfallet.extopy.usecases.posts

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.repositories.posts.IPostsRepository

class FetchPostUseCase(
    private val client: IExtopyClient,
    private val postsRepository: IPostsRepository,
) : IFetchPostUseCase {

    override suspend fun invoke(input: String): Post? {
        return postsRepository.get(input) ?: client.posts.get(input)?.also {
            postsRepository.save(
                it,
                Clock.System.now().plus(60, DateTimeUnit.SECOND, TimeZone.currentSystemDefault())
            )
        }
    }

}
