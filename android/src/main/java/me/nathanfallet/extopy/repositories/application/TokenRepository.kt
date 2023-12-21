package me.nathanfallet.extopy.repositories.application

import android.content.Context

class TokenRepository(
    context: Context,
) : ITokenRepository {

    private val sharedPreferences = context.getSharedPreferences("extopy", Context.MODE_PRIVATE)

    override fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    override fun setToken(token: String?) {
        sharedPreferences.edit()
            .putString("token", token)
            .apply()
    }

    override fun getUserId(): String? {
        return sharedPreferences.getString("userId", null)
    }

    override fun setUserId(userId: String?) {
        sharedPreferences.edit()
            .putString("userId", userId)
            .apply()
    }

}
