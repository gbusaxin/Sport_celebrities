package com.example.sportcelebrities.data.repository

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.Log
import android.webkit.WebSettings
import androidx.core.os.ConfigurationCompat
import com.example.sportcelebrities.data.remote.ApiService
import com.example.sportcelebrities.domain.model.RequestDto
import com.example.sportcelebrities.domain.model.ResponseDto
import com.example.sportcelebrities.domain.repository.WebViewOperations

class WebViewOperationsImpl(
    private val apiService: ApiService
) : WebViewOperations {

    override suspend fun sendLocale(): ResponseDto {
        val request = getLocale()
        return apiService.sendLocale(request)
    }

    private fun getLocale(): RequestDto {
        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
        val lang = locale[0]
        Log.d("CHECK_LOCALE",lang.isO3Language)
        return RequestDto(lang.isO3Language)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setWebViewSettings(settings: WebSettings) {
        with(settings) {
            domStorageEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            displayZoomControls = false
            allowFileAccess = true
            allowContentAccess = true
            javaScriptEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            userAgentString = settings.userAgentString.replace("; wv", "")
            javaScriptCanOpenWindowsAutomatically = true
            databaseEnabled = true
        }
    }
}