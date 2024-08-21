package com.yongdd.domain.model.routine

import com.yongdd.core.common.utils.RoutineDTO

data class RoutineModel(
    val id: Int,
    val content : String?,
    val detail : String?,
    val daysOfWeek : List<String>?,
    val emoticon : String?,
    val registrationDate : String?
)

fun RoutineModel.asRoutineDTO() = RoutineDTO(
    routineId = id,
    daysOfWeek = daysOfWeek?: emptyList(),
    routineContent = content ?:"",
    routineDetail = detail?:"",
    routineEmoticon = emoticon?:""
)