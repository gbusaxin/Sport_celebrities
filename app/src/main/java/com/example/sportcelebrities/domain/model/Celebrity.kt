package com.example.sportcelebrities.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sportcelebrities.utils.Constants.CELEBRITY_DB_TABLE

@Entity(tableName = CELEBRITY_DB_TABLE)
data class Celebrity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val power: Int,
    val month: String,
    val day: String,
    val family: List<String>,
    val abilities: List<String>,
    val natureTypes: List<String>
)