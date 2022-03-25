package com.example.sportcelebrities.domain.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sportcelebrities.utils.Constants.CELEBRITY_DB_TABLE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Entity(tableName = CELEBRITY_DB_TABLE)
data class Celebrity(
    @SerialName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val image: String,
    @SerialName("about")
    val about: String,
    @SerialName("rating")
    val rating: Double,
    @SerialName("power")
    val power: Int,
    @SerialName("month")
    val month: String,
    @SerialName("day")
    val day: String,
    @SerialName("teams")
    val teams: List<String>,
    @SerialName("prizes")
    val prizes: List<String>,
)