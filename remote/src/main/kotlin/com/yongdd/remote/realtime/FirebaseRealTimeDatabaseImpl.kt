package com.yongdd.remote.realtime

import com.google.firebase.database.DatabaseReference
import com.yongdd.core.common.log.Logger
import com.yongdd.core.common.wrapper.CALL_BACK_EMPTY
import com.yongdd.core.common.wrapper.CALL_BACK_FAIL
import com.yongdd.core.common.wrapper.CALL_BACK_SUCCESS
import com.yongdd.core.common.wrapper.CallBackResult
import com.yongdd.core.common.wrapper.CallBackSuccess
import com.yongdd.core.common.wrapper.CallBackFail
import com.yongdd.remote.realtime.model.UserDto
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class FirebaseRealTimeDatabaseImpl @Inject constructor(
    private val databaseReference: DatabaseReference
) : FirebaseRealTimeDatabase {

    override suspend fun getUserInfo(userId: String): CallBackResult<UserDto> {
        return suspendCancellableCoroutine { cancellableContinuation ->
            databaseReference.child(userId).get().addOnSuccessListener { data ->
                Logger.i("getUserInfo : $data")
                val response = data?.getValue(UserDto::class.java)?.let { userDto ->
                    CallBackSuccess(code = CALL_BACK_SUCCESS, data = userDto)
                } ?: CallBackSuccess(code = CALL_BACK_EMPTY, data = UserDto())
                cancellableContinuation.resume(response)
            }.addOnFailureListener {
                cancellableContinuation.resume(
                    CallBackFail(code = CALL_BACK_FAIL, message = it.message.toString())
                )
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