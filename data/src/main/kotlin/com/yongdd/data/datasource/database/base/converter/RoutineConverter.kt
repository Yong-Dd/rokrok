package com.yongdd.data.datasource.database.base.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yongdd.data.datasource.database.source.routine.model.RoutineData

@ProvidedTypeConverter
class RoutineConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromRoutineModelToString(data: RoutineData): String? {
        return gson.toJson(data)
    }

    @TypeConverter
    fun fromStringToRoutineModel(dataString: String): RoutineData? {
        val type = object : TypeToken<RoutineData>() {}.type
        return gson.fromJson(dataString, type)
    }
}