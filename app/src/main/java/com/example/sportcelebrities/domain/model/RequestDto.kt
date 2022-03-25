package com.example.sportcelebrities.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestDto(
    var request: String
)