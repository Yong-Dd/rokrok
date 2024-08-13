package com.yongdd.firebase.realtime

import com.yongdd.app.rokrok.firebase.FirebaseRealTimeDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRealTimeDatabaseImpl @Inject constructor(
    private val firebaseDatabase: FirebaseRealTimeDatabase
) {

}