package com.yongdd.data.datasource.database.base.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yongdd.domain.model.routine.RoutineModel

@ProvidedTypeConverter
class RoutineConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromRoutineModelToString(data: RoutineModel): String? {
        return gson.toJson(data)
    }

    @TypeConverter
    fun fromStringToRoutineModel(dataString: String): RoutineModel? {
        val type = object : TypeToken<RoutineModel>() {}.type
        return gson.fromJson(dataString, type)
    }
}