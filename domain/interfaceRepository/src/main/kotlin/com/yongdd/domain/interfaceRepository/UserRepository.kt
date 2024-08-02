package com.yongdd.domain.interfaceRepository

interface UserRepository {
    suspend fun getUserId() : String
    suspend fun setUserId(id: String)


}