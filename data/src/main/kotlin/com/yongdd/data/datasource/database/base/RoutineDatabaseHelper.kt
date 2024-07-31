package com.yongdd.data.datasource.database.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yongdd.data.datasource.database.base.converter.RoutineConverter
import com.yongdd.data.datasource.database.base.converter.StringListConverter
import com.yongdd.data.datasource.database.source.routine.dao.RoutineDatabaseDao
import com.yongdd.data.datasource.database.source.routine.model.RoutineData
import com.yongdd.data.datasource.database.source.routineSave.dao.RoutineSaveDatabaseDao
import com.yongdd.data.datasource.database.source.routineSave.model.RoutineSaveData
import kotlinx.coroutines.CoroutineScope

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        RoutineData::class,
        RoutineSaveData::class
    ]
)
@TypeConverters(
    value = [
        RoutineConverter::class,
        StringListConverter::class
    ]
)
abstract class RoutineDatabaseHelper : RoomDatabase() {
    abstract fun routineDao(): RoutineDatabaseDao
    abstract fun routineSaveDao() : RoutineSaveDatabaseDao

    companion object {

        @Volatile
        private var instance : RoutineDatabaseHelper? = null

        fun getDataBase(contextApplication : Context, scope : CoroutineScope) : RoutineDatabaseHelper {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(contextApplication, RoutineDatabaseHelper::class.java, "routine")
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
