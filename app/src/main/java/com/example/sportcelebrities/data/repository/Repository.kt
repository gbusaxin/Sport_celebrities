package com.example.sportcelebrities.data.repository

import androidx.paging.PagingData
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.domain.repository.DataStoreOperations
import com.example.sportcelebrities.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataStore: DataStoreOperations,
    private val remote: RemoteDataSource
) {

    fun getAllCelebrities():Flow<PagingData<Celebrity>>{
        return remote.getAllData()
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

}