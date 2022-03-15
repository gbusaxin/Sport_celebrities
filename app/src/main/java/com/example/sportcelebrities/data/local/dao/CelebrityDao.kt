package com.example.sportcelebrities.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportcelebrities.domain.model.Celebrity

@Dao
interface CelebrityDao {

    @Query("SELECT * FROM table_celebrity ORDER BY id ASC")
    fun getAllCelebrities(): PagingSource<Int, Celebrity>

    @Query("SELECT * FROM table_celebrity WHERE id =:id")
    fun getSelectedCelebrity(id: Int): Celebrity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCelebrities(list:List<Celebrity>)

    @Query("DELETE FROM table_celebrity")
    suspend fun deleteAllCelebrities()
}