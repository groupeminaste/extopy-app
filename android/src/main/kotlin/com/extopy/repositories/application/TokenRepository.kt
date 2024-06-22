package com.extopy.repositories.application

import android.content.Context
import dev.kaccelero.models.UUID

class TokenRepository(
    context: Context,
) : ITokenRepository {

    private val sharedPreferences = context.getSharedPreferences("extopy", Context.MODE_PRIVATE)

    override fun getToken(): String? = sharedPreferences.getString("token", null)

    override fun setToken(token: String?) = sharedPreferences.edit()
        .putString("token", token)
        .apply()

    override fun getRefreshToken(): String? = sharedPreferences.getString("refreshToken", null)

    override fun setRefreshToken(token: String?) = sharedPreferences.edit()
        .putString("refreshToken", token)
        .apply()

    override fun getUserId(): UUID? =
        sharedPreferences.getString("userId", null)?.let(::UUID)

    override fun setUserId(userId: UUID?) = sharedPreferences.edit()
        .putString("userId", userId.toString())
        .apply()

}
