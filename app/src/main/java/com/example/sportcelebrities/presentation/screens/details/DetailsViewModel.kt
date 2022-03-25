package com.example.sportcelebrities.presentation.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.domain.use_cases.UseCases
import com.example.sportcelebrities.utils.Constants.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedCelebrity: MutableStateFlow<Celebrity?> = MutableStateFlow(null)
    val selectedCelebrity: StateFlow<Celebrity?> = _selectedCelebrity

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val celebrityId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)
            _selectedCelebrity.value = celebrityId?.let { useCases.getSelectedCelebrityUseCase(it) }
        }
    }

}