package com.example.sportcelebrities.di

import android.content.Context
import androidx.room.Room
import com.example.sportcelebrities.data.local.AppDatabase
import com.example.sportcelebrities.data.local.AppDatabase_Impl
import com.example.sportcelebrities.data.repository.LocalDataSourceImpl
import com.example.sportcelebrities.domain.repository.LocalDataSource
import com.example.sportcelebrities.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        appDatabase: AppDatabase
    ):LocalDataSource{
        return LocalDataSourceImpl(appDatabase = appDatabase)
    }

}