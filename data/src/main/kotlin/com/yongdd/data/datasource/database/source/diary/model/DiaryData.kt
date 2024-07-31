package com.yongdd.data.datasource.database.source.diary.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yongdd.domain.model.diary.DiaryModel

/**
 * title : 일기 제목
 * content : 일기 내용
 * yearMonth : 작성 년.월(yyyy.MM)
 * day : 작성일
 * moodScore : 기분 점수
 * */
@Entity(tableName = "DiaryData")
data class DiaryData(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String? = null,
    val content : String? = null,
    val yearMonth : String? = null,
    val day : Int? = null,
    val moodScore : Int? = null
)

fun DiaryData.asModel() = DiaryModel(
    id = id,
    title = title,
    content = content,
    yearMonth = yearMonth,
    day = day,
    moodScore = moodScore
)

fun DiaryModel.asData()  = DiaryData(
    id = id,
    title = title,
    content = content,
    yearMonth = yearMonth,
    day = day,
    moodScore = moodScore
)