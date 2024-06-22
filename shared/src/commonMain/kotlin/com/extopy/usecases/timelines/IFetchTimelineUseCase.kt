package com.extopy.usecases.timelines

import com.extopy.models.timelines.Timeline
import dev.kaccelero.models.UUID
import dev.kaccelero.usecases.ISuspendUseCase

interface IFetchTimelineUseCase : ISuspendUseCase<UUID, Timeline?>
