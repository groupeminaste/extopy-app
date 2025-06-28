package com.extopy.models.navigation

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import dev.kaccelero.models.UUID

internal val UUIDNavType = object : NavType<UUID>(isNullableAllowed = false) {
    override fun get(bundle: SavedState, key: String): UUID = bundle.read { UUID(getString(key)) }
    override fun put(bundle: SavedState, key: String, value: UUID) = bundle.write { putString(key, value.toString()) }
    override fun parseValue(value: String): UUID = UUID(value)
    override fun serializeAsValue(value: UUID): String = value.toString()
}

internal val optionalUUIDNavType = object : NavType<UUID?>(isNullableAllowed = true) {
    override fun get(bundle: SavedState, key: String): UUID? =
        bundle.read { getString(key).takeIf { it != "null" }?.let(::UUID) }

    override fun put(bundle: SavedState, key: String, value: UUID?) = bundle.write { putString(key, value.toString()) }
    override fun parseValue(value: String): UUID? = value.takeIf { it != "null" }?.let(::UUID)
    override fun serializeAsValue(value: UUID?): String = value.toString()
}
