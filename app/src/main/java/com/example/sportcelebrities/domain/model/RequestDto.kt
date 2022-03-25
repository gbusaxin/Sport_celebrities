package com.example.sportcelebrities.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RequestDto(
    @SerialName("locale")
    var request: String
)