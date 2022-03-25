package com.example.sportcelebrities.domain.use_cases

import android.webkit.WebSettings
import com.example.sportcelebrities.data.repository.Repository

class SetWebSettingsUseCase(
    private val repository: Repository
) {
    operator fun invoke(settings: WebSettings) = repository.setWebViewSettings(settings = settings)
}