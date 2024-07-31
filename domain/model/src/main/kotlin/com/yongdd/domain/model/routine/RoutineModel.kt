package com.yongdd.domain.model.routine

data class RoutineModel(
    val id: Int,
    val content : String?,
    val detail : String?,
    val daysOfWeek : List<String>?,
    val emoticon : String?,
    val registrationDate : String?
)