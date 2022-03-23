package com.example.sportcelebrities.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sportcelebrities.utils.Constants.CELEBRITY_DB_REMOTE_KEY

@Entity(tableName = CELEBRITY_DB_REMOTE_KEY)
data class CelebrityRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val prevKey:Int?,
    val nextKey:Int?,
    val lastUpdate:Long?
)
