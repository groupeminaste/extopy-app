package me.nathanfallet.extopy.usecases.timelines

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.timelines.Timeline

class FetchTimelineUseCase(
    private val client: IExtopyClient,
) : IFetchTimelineUseCase {

    override suspend fun invoke(input: String): Timeline? = client.timelines.get(input)

}
