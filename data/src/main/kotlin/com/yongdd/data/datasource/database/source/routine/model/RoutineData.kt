package com.yongdd.data.datasource.database.source.routine.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yongdd.data.datasource.database.base.converter.StringListConverter
import com.yongdd.domain.model.routine.RoutineModel


/**
 * content : 루틴 내용
 * detail : 루틴 상세 내용
 * dayOfWeek : 루틴 요일들 ([“mon”,”wed”])
 * emoticon : 루틴 이모티콘
 * registrationDate : 루틴 등록 일자 (yyyyMMdd)
 * */
@Entity(tableName = "RoutineData")
data class RoutineData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content : String? = null,
    val detail : String? = null,
    @TypeConverters(StringListConverter::class)
    val daysOfWeek : List<String>? = null,
    val emoticon : String? = null,
    val registrationDate : String? = null
)

fun RoutineData.asModel() = RoutineModel(
    id = id,
    content = content,
    detail = detail,
    daysOfWeek = daysOfWeek,
    emoticon = emoticon,
    registrationDate = registrationDate
)

fun RoutineModel.asData()  = RoutineData(
    id = id,
    content = content,
    detail = detail,
    daysOfWeek = daysOfWeek,
    emoticon = emoticon,
    registrationDate = registrationDate
)