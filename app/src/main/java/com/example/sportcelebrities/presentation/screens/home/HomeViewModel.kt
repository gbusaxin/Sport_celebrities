package com.example.sportcelebrities.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.sportcelebrities.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCases: UseCases
): ViewModel() {
    val getAllCelebrities = useCases.getAllCelebritiesUseCase()
}