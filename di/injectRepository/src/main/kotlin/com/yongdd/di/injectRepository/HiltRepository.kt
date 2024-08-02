package com.yongdd.di.injectRepository

import com.yongdd.data.repository.DiaryRepositoryImpl
import com.yongdd.data.repository.RoutineRepositoryImpl
import com.yongdd.data.repository.RoutineSaveRepositoryImpl
import com.yongdd.data.repository.UserRepositoryImpl
import com.yongdd.domain.interfaceRepository.DiaryRepository
import com.yongdd.domain.interfaceRepository.RoutineRepository
import com.yongdd.domain.interfaceRepository.RoutineSaveRepository
import com.yongdd.domain.interfaceRepository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HiltRepository {
    @Binds
    fun bindDiaryRepository(repository: DiaryRepositoryImpl): DiaryRepository

    @Binds
    fun bindRoutineRepository(repository: RoutineRepositoryImpl): RoutineRepository

    @Binds
    fun bindRoutineSaveRepository(repository: RoutineSaveRepositoryImpl): RoutineSaveRepository

    @Binds
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}