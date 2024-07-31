package com.yongdd.domain.model.routine

data class RoutineSaveModel (
    val saveId : String,
    val routineId : Int? = null,
    val routineDay : String? = null,
    val percent : Int = 0,
    val isShow : Boolean = true,
    val routine : RoutineModel? = null
)