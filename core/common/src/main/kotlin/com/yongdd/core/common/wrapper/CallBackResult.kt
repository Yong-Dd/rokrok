package com.yongdd.core.common.wrapper

sealed interface CallBackResult<out T>

data class CallBackSuccess<T>(val code: Int?, val data: T) : CallBackResult<T>
sealed interface Fail : CallBackResult<Nothing> {
    val code : Int?
    val message : String?
}

data class CallBackFail(override val code: Int?, override val message: String?):Fail

const val CALL_BACK_SUCCESS = 200
const val CALL_BACK_EMPTY = 300
const val CALL_BACK_FAIL = 400