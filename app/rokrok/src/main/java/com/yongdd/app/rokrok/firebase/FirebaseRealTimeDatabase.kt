package com.yongdd.app.rokrok.firebase

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRealTimeDatabase()  {

    companion object {
        private val databaseReference = Firebase.database.reference
    }

    fun getUserInfoFromId(userId: String, onGetResult: (String) -> Unit) {
        databaseReference.child(userId).get().addOnSuccessListener {
            onGetResult(it.value.toString())
        }.addOnFailureListener {
            onGetResult(it.toString())
        }
    }

    fun setUserInfo(
        userId: String,
        nickName: String,
        message: String,
        settingEmoji: String,
        lastUpdateDate: String,
        onGetResult: (Int) -> Unit
    ) {
        val userInfo = hashMapOf(
            "nickName" to nickName,
            "message" to message,
            "settingEmoji" to settingEmoji,
            "lastUpdateDate" to lastUpdateDate)
        databaseReference.child(userId).setValue(userInfo).addOnSuccessListener {
            onGetResult(1)
        }.addOnFailureListener {
            onGetResult(0)
        }
    }
}