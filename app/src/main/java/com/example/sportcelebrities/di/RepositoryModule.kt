package com.example.sportcelebrities.di

import android.content.Context
import com.example.sportcelebrities.data.repository.DataStoreOperationsImpl
import com.example.sportcelebrities.data.repository.Repository
import com.example.sportcelebrities.domain.repository.DataStoreOperations
import com.example.sportcelebrities.domain.use_cases.*
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
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository = repository),
            getAllCelebritiesUseCase = GetAllCelebritiesUseCase(repository = repository),
            searchCelebrityUseCase = SearchCelebrityUseCase(repository = repository),
            getSelectedCelebrityUseCase = GetSelectedCelebrityUseCase(repository = repository),
            sendLocaleUseCase = SendLocaleUseCase(repository = repository),
            setWebSettingsUseCase = SetWebSettingsUseCase(repository = repository)
        )
    }

}