package com.example.sportcelebrities.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.sportcelebrities.data.local.AppDatabase
import com.example.sportcelebrities.data.paging_source.CelebrityRemoteMediator
import com.example.sportcelebrities.data.remote.ApiService
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.domain.repository.RemoteDataSource
import com.example.sportcelebrities.utils.Constants.PAGE_SIZE_DATABASE
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val apiService: ApiService,
    private val database: AppDatabase
) : RemoteDataSource {

    val celebrityDao = database.getCelebrityDao()

    override fun getAllData(): Flow<PagingData<Celebrity>> {
        val pagingSourceFactory = { celebrityDao.getAllCelebrities() }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE_DATABASE),
            remoteMediator = CelebrityRemoteMediator(
                apiService = apiService,
                appDatabase = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(): Flow<PagingData<Celebrity>> {
        TODO("Not yet implemented")
    }

}