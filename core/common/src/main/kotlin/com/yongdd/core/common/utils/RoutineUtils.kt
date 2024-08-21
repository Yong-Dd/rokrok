package com.yongdd.core.common.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class RoutineDTO (
    val routineId : Int,
    val daysOfWeek : List<String>,
    val routineContent : String,
    val routineDetail : String,
    val routineEmoticon : String
)

fun getDayOfWeek(dayOfWeek: Int): String {
    return dayOfWeekMap[dayOfWeek] ?: "UNKNOWN"
}

// 요일 숫자와 문자 매핑 -> todo :enum 추가할지 고민하기
val dayOfWeekMap = mapOf(
    1 to "SUN",
    2 to "MON",
    3 to "TUE",
    4 to "WED",
    5 to "THU",
    6 to "FRI",
    7 to "SAT"
)

object RoutineUtils {
    private val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd")

    suspend fun saveRoutinesBeforeToday(
        saveStartDate : String,
        routineList : List<RoutineDTO>,
        onGetSaveRoutineList : suspend (searchDate:String) -> List<RoutineDTO>,
        onSaveRoutine : (String, RoutineDTO) -> Unit,
        onComplete : () -> Unit
    ) {

        val today : LocalDate = LocalDate.now()
        val baseDate : LocalDate = LocalDate.parse(saveStartDate, dateFormat)

        generateSequence(baseDate) { it.plusDays(1) }
            .takeWhile { !it.isAfter(today) }
            .forEach { currentDate ->
                val currentDateStr : String = currentDate.format(dateFormat)

                routineList.forEach { routine ->
                    if (routine.daysOfWeek.contains(getDayOfWeek(currentDate.dayOfWeek.value)) &&
                        onGetSaveRoutineList(currentDateStr).none { it.routineId == routine.routineId }) {
                        onSaveRoutine(currentDateStr, routine)
                    }
                }
            }

        onComplete()
    }
}