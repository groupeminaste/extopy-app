package me.nathanfallet.extopy.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DatabaseDriverFactory(private val context: Context) {

    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(AppDatabase.Schema, context, "extopy.db")

}
