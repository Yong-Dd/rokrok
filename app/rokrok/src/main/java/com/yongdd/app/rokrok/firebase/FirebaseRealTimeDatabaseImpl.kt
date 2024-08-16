package com.yongdd.app.rokrok.firebase

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yongdd.core.common.wrapper.CALL_BACK_EMPTY
import com.yongdd.core.common.wrapper.CALL_BACK_SUCCESS
import com.yongdd.remote.realtime.FirebaseRealTimeDatabase
import com.yongdd.core.common.wrapper.CallBackResult
import com.yongdd.core.common.wrapper.CallBackSuccess
import com.yongdd.core.common.wrapper.CallBackFail
import com.yongdd.remote.realtime.model.UserDto
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirebaseRealTimeDatabaseImpl : FirebaseRealTimeDatabase {

    companion object {
        private val databaseReference = Firebase.database.reference
    }

    override suspend fun getUserInfo(userId: String): CallBackResult<UserDto> { // todo : 차후 null 처리 합칠 수 있을지 고려하기
        return suspendCancellableCoroutine { cancellableContinuation ->
            databaseReference.child(userId).get().addOnSuccessListener { data ->
              data?.let {
                  cancellableContinuation.resume(CallBackSuccess(code = CALL_BACK_SUCCESS, data = it.getValue(UserDto::class.java)!!))
              } ?: cancellableContinuation.resume(CallBackSuccess(code = CALL_BACK_EMPTY, data = UserDto()))
            }.addOnFailureListener {
                cancellableContinuation.resume(CallBackFail(code = 1, message = it.message.toString()))
            }
        }
    }

    override suspend fun setUserInfo(
        userId: String,
        nickName: String,
        message: String,
        settingEmoji: String,
        lastUpdateDate: String
    ): CallBackResult<String> {
        return suspendCancellableCoroutine { cancellableContinuation ->
            val userInfo = hashMapOf(
                "nickName" to nickName,
                "message" to message,
                "settingEmoji" to settingEmoji,
                "lastUpdateDate" to lastUpdateDate)
            databaseReference.child(userId).setValue(userInfo).addOnSuccessListener {
                cancellableContinuation.resume(CallBackSuccess(code = CALL_BACK_SUCCESS, data = ""))
            }.addOnFailureListener {
                cancellableContinuation.resume(CallBackFail(code = 1, message = it.message.toString()))
            }
        }

    }
}