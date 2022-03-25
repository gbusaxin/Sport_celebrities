package com.example.sportcelebrities.presentation.screens.web_view

import android.webkit.WebSettings
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportcelebrities.domain.use_cases.UseCases
import com.example.sportcelebrities.utils.Constants.SERVER_RESPONSE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _linkResponse: MutableStateFlow<String?> = MutableStateFlow(null)
    val linkResponse: StateFlow<String?> = _linkResponse

    fun setWebSettings(settings: WebSettings) {
        useCases.setWebSettingsUseCase(settings = settings)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _linkResponse.value = savedStateHandle.get<String>(SERVER_RESPONSE_KEY)
        }
    }

}