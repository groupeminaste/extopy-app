package com.extopy.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val databaseDispatcher: CoroutineContext = Dispatchers.IO

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<ExtopyDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(ExtopyDatabase.FILE_NAME)
    return Room.databaseBuilder<ExtopyDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}
