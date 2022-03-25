package com.example.sportcelebrities.data.repository

import com.example.sportcelebrities.data.local.AppDatabase
import com.example.sportcelebrities.domain.repository.LocalDataSource

class LocalDataSourceImpl(
    appDatabase: AppDatabase
) : LocalDataSource {

    private val celebrityDao = appDatabase.getCelebrityDao()

    override suspend fun getSelectedCelebrity(id: Int) = celebrityDao.getSelectedCelebrity(id)

}