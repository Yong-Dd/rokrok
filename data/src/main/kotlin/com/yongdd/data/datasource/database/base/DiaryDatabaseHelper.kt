package com.yongdd.data.datasource.database.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yongdd.data.datasource.database.source.diary.dao.DiaryDatabaseDao
import com.yongdd.data.datasource.database.source.diary.model.DiaryData
import kotlinx.coroutines.CoroutineScope

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        DiaryData::class
    ]
)
abstract class DiaryDatabaseHelper : RoomDatabase() {
    abstract fun diaryDao(): DiaryDatabaseDao

    companion object {

        @Volatile
        private var instance : DiaryDatabaseHelper? = null

        fun getDataBase(contextApplication : Context, scope : CoroutineScope) : DiaryDatabaseHelper {
            return instance ?: synchronized(this) {
                val database = Room.databaseBuilder(contextApplication, DiaryDatabaseHelper::class.java, "diary")
                    .addCallback(CallbackDatabaseDiary(scope))
                    .build()
                instance = database
                database
            }
        }

        private class CallbackDatabaseDiary(
            private val scope : CoroutineScope,
        ) : Callback() {
            override fun onCreate(db : SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}