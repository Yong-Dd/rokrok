package com.yongdd.data.datasource.database.source.routineSave.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yongdd.domain.model.routine.RoutineSaveModel

/**
 * saveId : 루틴 진행 일자 + 루틴 아이디 (yyyyMMdd_id)
 * routineDate : 루틴 진행 일자
 * percent : 진행 달성도(퍼센트)
 * isShow : 루틴을 보여줄지 여부
 * [routine ~ : 저장 상태 때의 루틴]
 * routineId : 진행 루틴 아이디
 * routineContent :루틴 내용
 * routineDetail : 루틴 상세
 * routineEmoticon : 루틴 이모티콘
 * */
@Entity(tableName = "RoutineSaveData")
data class RoutineSaveData(
    @PrimaryKey
    val saveId : String,
    val routineId : Int,
    val routineDay : String? = null,
    val percent : Int = 0,
    val isShow : Boolean = true,
    val routineContent : String? = null,
    val routineDetail : String? = null,
    val routineEmoticon : String? = null,
)

fun RoutineSaveData.asModel() = RoutineSaveModel(
    saveId = saveId,
    routineId =  routineId,
    routineDay = routineDay,
    percent = percent,
    isShow = isShow,
    routineContent = routineContent,
    routineDetail = routineDetail,
    routineEmoticon = routineEmoticon
)

fun RoutineSaveModel.asData() = RoutineSaveData(
    saveId = saveId,
    routineId = routineId,
    routineDay = routineDay,
    percent = percent,
    isShow = isShow,
    routineContent = routineContent,
    routineDetail = routineDetail,
    routineEmoticon = routineEmoticon
)