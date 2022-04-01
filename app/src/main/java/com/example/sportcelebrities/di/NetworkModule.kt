package com.example.sportcelebrities.di

import androidx.paging.ExperimentalPagingApi
import com.example.sportcelebrities.data.local.AppDatabase
import com.example.sportcelebrities.data.remote.ApiService
import com.example.sportcelebrities.data.repository.RemoteDataSourceImpl
import com.example.sportcelebrities.data.repository.WebViewOperationsImpl
import com.example.sportcelebrities.domain.repository.RemoteDataSource
import com.example.sportcelebrities.domain.repository.WebViewOperations
import com.example.sportcelebrities.utils.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: ApiService,
        appDatabase: AppDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(apiService = apiService, database = appDatabase)
    }

    @Provides
    @Singleton
    fun provideWebViewOperations(
        apiService: ApiService
    ): WebViewOperations {
        return WebViewOperationsImpl(apiService = apiService)
    }
}