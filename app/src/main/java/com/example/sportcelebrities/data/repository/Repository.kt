package com.example.sportcelebrities.data.repository

import android.webkit.WebSettings
import androidx.paging.PagingData
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.domain.model.ResponseDto
import com.example.sportcelebrities.domain.repository.DataStoreOperations
import com.example.sportcelebrities.domain.repository.LocalDataSource
import com.example.sportcelebrities.domain.repository.RemoteDataSource
import com.example.sportcelebrities.domain.repository.WebViewOperations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val dataStore: DataStoreOperations,
    private val remote: RemoteDataSource,
    private val webViewOperations: WebViewOperations
) {

    suspend fun getSelectedCelebrity(id: Int): Celebrity {
        return local.getSelectedCelebrity(id)
    }

    fun getAllCelebrities(): Flow<PagingData<Celebrity>> {
        return remote.getAllData()
    }

    fun searchCelebrity(query: String): Flow<PagingData<Celebrity>> {
        return remote.searchHeroes(query = query)
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

    suspend fun sendLocale(): ResponseDto {
        return webViewOperations.sendLocale()
    }

    fun setWebViewSettings(settings: WebSettings) {
        webViewOperations.setWebViewSettings(settings = settings)
    }
}