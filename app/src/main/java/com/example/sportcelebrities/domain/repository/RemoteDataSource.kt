package com.example.sportcelebrities.domain.repository

import androidx.paging.PagingData
import com.example.sportcelebrities.domain.model.Celebrity
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllData(): Flow<PagingData<Celebrity>>
    fun searchHeroes(query:String):Flow<PagingData<Celebrity>>

}