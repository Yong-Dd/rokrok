package com.yongdd.app.rokrok.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseDI {
    @Provides
    fun provideFirebaseDatabaseReference() : DatabaseReference {
        return Firebase.database.reference
    }
}