package com.example.sportcelebrities.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportcelebrities.domain.model.CelebrityRemoteKey

@Dao
interface CelebrityRemoteDao {

    @Query("SELECT * FROM celebrity_remote_key WHERE id =:id")
    suspend fun getRemoteKey(id: Int): CelebrityRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKey(celebrityRemoteKeys:List<CelebrityRemoteKey>)

    @Query("DELETE FROM celebrity_remote_key")
    suspend fun deleteAllRemoteKeys()

}