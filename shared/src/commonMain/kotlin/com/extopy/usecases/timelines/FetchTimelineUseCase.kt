package com.extopy.usecases.timelines

import com.extopy.client.IExtopyClient
import com.extopy.models.timelines.Timeline
import dev.kaccelero.models.UUID

class FetchTimelineUseCase(
    private val client: IExtopyClient,
) : IFetchTimelineUseCase {

    override suspend fun invoke(input: UUID): Timeline? = client.timelines.get(input)

}
