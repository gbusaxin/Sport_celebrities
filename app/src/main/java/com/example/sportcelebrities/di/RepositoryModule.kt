package com.example.sportcelebrities.di

import android.content.Context
import com.example.sportcelebrities.data.repository.DataStoreOperationsImpl
import com.example.sportcelebrities.data.repository.Repository
import com.example.sportcelebrities.domain.repository.DataStoreOperations
import com.example.sportcelebrities.domain.use_cases.ReadOnBoardingUseCase
import com.example.sportcelebrities.domain.use_cases.SaveOnBoardingUseCase
import com.example.sportcelebrities.domain.use_cases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesDataStoreOperations(
        @ApplicationContext context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository = repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository = repository)
        )
    }

}