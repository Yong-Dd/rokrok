package com.yongdd.data.datasource.database.source.routineSave.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yongdd.data.datasource.database.base.converter.RoutineConverter
import com.yongdd.data.datasource.database.source.routine.model.RoutineData
import com.yongdd.data.datasource.database.source.routine.model.asData
import com.yongdd.data.datasource.database.source.routine.model.asModel
import com.yongdd.domain.model.routine.RoutineSaveModel

/**
 * saveId : 루틴 실제 진행 일자 + 루틴 아이디 (yyyyMMdd_id)
 * routineId : 진행 루틴 아이디
 * routineDay : 루틴 실제 진행 일자
 * percent : 진행 달성도(퍼센트)
 * isShow : 루틴을 보여줄지 여부
 * routine : 저장 상태 때의 루틴 ({“content”:”타이틀”,”detail”:”디테일”...})
 * */
@Entity(tableName = "RoutineSaveData")
data class RoutineSaveData(
    @PrimaryKey
    val saveId : String,
    val routineId : Int? = null,
    val routineDay : String? = null,
    val percent : Int = 0,
    val isShow : Boolean = true,
    @TypeConverters(RoutineConverter::class)
    val routineInfo : RoutineData? = null
)

fun RoutineSaveData.asModel() = RoutineSaveModel(
    saveId = saveId,
    routineId = routineId,
    routineDay = routineDay,
    percent = percent,
    isShow = isShow,
    routine = routineInfo?.asModel()
)

fun RoutineSaveModel.asData() = RoutineSaveData(
    saveId = saveId,
    routineId = routineId,
    routineDay = routineDay,
    percent = percent,
    isShow = isShow,
    routineInfo = routine?.asData()
)