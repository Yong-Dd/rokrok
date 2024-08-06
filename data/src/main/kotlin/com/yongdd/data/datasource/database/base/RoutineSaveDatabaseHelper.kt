package com.yongdd.data.datasource.database.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yongdd.data.datasource.database.base.converter.StringListConverter
import com.yongdd.data.datasource.database.source.routine.dao.RoutineDatabaseDao
import com.yongdd.data.datasource.database.source.routineSave.dao.RoutineSaveDatabaseDao
import com.yongdd.data.datasource.database.source.routineSave.model.RoutineSaveData
import kotlinx.coroutines.CoroutineScope

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        RoutineSaveData::class
    ]
)
@TypeConverters(
    value = [
        StringListConverter::class
    ]
)
abstract class RoutineSaveDatabaseHelper : RoomDatabase() {
    abstract fun routineSaveDao() : RoutineSaveDatabaseDao

    companion object {

        @Volatile
        private var instance : RoutineSaveDatabaseHelper? = null

        fun getDataBase(contextApplication : Context, scope : CoroutineScope) : RoutineSaveDatabaseHelper {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(contextApplication, RoutineSaveDatabaseHelper::class.java, "RoutineSaveData")
                    .addCallback(CallbackDatabaseRoutine(scope))
                    .build()
                instance = database
                database
            }
        }

        private class CallbackDatabaseRoutine(
            private val scope : CoroutineScope,
        ) : Callback() {
            override fun onCreate(db : SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}
