package com.example.sportcelebrities.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sportcelebrities.data.local.dao.CelebrityDao
import com.example.sportcelebrities.data.local.dao.CelebrityRemoteDao
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.domain.model.CelebrityRemoteKey

@Database(
    entities = [
        Celebrity::class,
        CelebrityRemoteKey::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(DatabaseConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCelebrityDao(): CelebrityDao
    abstract fun getCelebrityRemoteDao(): CelebrityRemoteDao

}