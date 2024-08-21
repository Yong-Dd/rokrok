package com.yongdd.domain.model.routine

import com.yongdd.core.common.utils.RoutineDTO

data class RoutineSaveModel (
    val saveId : String,
    val routineId : Int,
    val routineDay : String? = null,
    val percent : Int = 0,
    val isShow : Boolean = true,
    val routineContent : String? = null,
    val routineDetail : String? = null,
    val routineEmoticon : String? = null,
)

fun RoutineSaveModel.asRoutineDTO() = RoutineDTO(
    routineId = routineId,
    daysOfWeek = emptyList(),
    routineContent = routineContent ?:"",
    routineDetail = routineDetail?:"",
    routineEmoticon = routineEmoticon?:""
)