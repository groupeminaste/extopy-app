package me.nathanfallet.extopy.usecases.timelines

import me.nathanfallet.extopy.models.timelines.Timeline
import me.nathanfallet.usecases.base.ISuspendUseCase

interface IFetchTimelineUseCase : ISuspendUseCase<String, Timeline?>
