package com.example.sportcelebrities.domain.repository

import android.webkit.WebSettings
import com.example.sportcelebrities.domain.model.ResponseDto

interface WebViewOperations {
    suspend fun sendLocale(): ResponseDto
    fun setWebViewSettings(settings: WebSettings)
}