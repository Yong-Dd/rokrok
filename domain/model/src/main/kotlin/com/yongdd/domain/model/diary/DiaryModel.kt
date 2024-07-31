package com.yongdd.domain.model.diary

data class DiaryModel(
    val id : Int,
    val title : String?,
    val content : String?,
    val yearMonth : String?,
    val day : Int?,
    val moodScore : Int?
)