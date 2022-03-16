package com.example.sportcelebrities.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveOnBoardingState(completed:Boolean)
    fun readOnBoardingState():Flow<Boolean>
}