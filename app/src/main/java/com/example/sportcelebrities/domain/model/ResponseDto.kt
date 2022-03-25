package com.example.sportcelebrities.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body

@Keep
@Serializable
data class ResponseDto(
    @SerialName("url")
    var url: String
)