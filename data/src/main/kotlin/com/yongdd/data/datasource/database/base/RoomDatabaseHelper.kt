package com.yongdd.data.datasource.database.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yongdd.data.datasource.database.base.converter.StringListConverter
import com.yongdd.data.datasource.database.source.diary.dao.DiaryDatabaseDao
import com.yongdd.data.datasource.database.source.diary.model.DiaryData
import com.yongdd.data.datasource.database.source.routine.dao.RoutineDatabaseDao
import com.yongdd.data.datasource.database.source.routine.model.RoutineData
import com.yongdd.data.datasource.database.source.routineSave.dao.RoutineSaveDatabaseDao
import com.yongdd.data.datasource.database.source.routineSave.model.RoutineSaveData
import com.yongdd.data.datasource.database.source.user.dao.UserDatabaseDao
import com.yongdd.data.datasource.database.source.user.model.UserData
import com.yongdd.data.datasource.datastore.UserDatastore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        RoutineData::class,
        RoutineSaveData::class,
        DiaryData::class,
        UserData::class
    ]
)
@TypeConverters(
    value = [
        StringListConverter::class
    ]
)
abstract class RoomDatabaseHelper : RoomDatabase() {
    abstract fun routineDao(): RoutineDatabaseDao
    abstract fun routineSaveDao() : RoutineSaveDatabaseDao
    abstract fun diaryDao() : DiaryDatabaseDao
    abstract fun userDao() : UserDatabaseDao


    companion object {

        @Volatile
        private var instance : RoomDatabaseHelper? = null
        private var cachedUserId : String? = null

        fun getDataBase(contextApplication : Context, scope : CoroutineScope, userDatastore: UserDatastore) : RoomDatabaseHelper {
            return instance ?: synchronized(this) {
                val userId: String = cachedUserId ?: runBlocking {
                    val deferredUserId = scope.async {
                        userDatastore.getUserId()
                    }
                    val userId = deferredUserId.await()
                    cachedUserId = userId
                    userId
                }

                val database = Room.databaseBuilder(contextApplication, RoomDatabaseHelper::class.java, "rokrok_$userId")
                    .addCallback(CallbackDatabaseRoutine(scope))
                    .addTypeConverter(StringListConverter())
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
