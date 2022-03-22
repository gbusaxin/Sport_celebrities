package com.example.sportcelebrities.domain.use_cases

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllCelebritiesUseCase: GetAllCelebritiesUseCase
)