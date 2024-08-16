package com.yongdd.core.common.wrapper

sealed interface CallBackResult<out T>

data class CallBackSuccess<T>(val code: Int?, val data: T) : CallBackResult<T>

sealed interface Fail : CallBackResult<Nothing> {
    val code : Int?
    val message : String?
}

data class CallBackFail(override val code: Int?, override val message: String?):Fail

const val CALL_BACK_SUCCESS = 100
const val CALL_BACK_EMPTY = 200