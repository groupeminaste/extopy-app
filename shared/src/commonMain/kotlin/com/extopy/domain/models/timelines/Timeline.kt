package com.extopy.domain.models.timelines

import kotlin.uuid.Uuid

/**
 * A timeline, as the app knows one.
 *
 * The server decides what a timeline contains; from here it is only an identity to read posts
 * through.
 */
data class Timeline(
    val id: Uuid,
) {

    companion object {
        /**
         * The timeline every signed-in user lands on. The server reads the nil UUID as "whatever
         * this user's home feed is", so the app does not have to discover an id before it can draw
         * anything.
         */
        val defaultId: Uuid = Uuid.parse("00000000-0000-0000-0000-000000000000")
    }

}
