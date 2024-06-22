package com.extopy.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

class DatabaseDriverFactory(private val context: Context) : IDatabaseDriverFactory {

    override fun createDriver(): SqlDriver =
        AndroidSqliteDriver(AppDatabase.Schema, context, "extopy.db")

}
