package com.example.sportcelebrities.domain.use_cases

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllCelebritiesUseCase: GetAllCelebritiesUseCase,
    val searchCelebrityUseCase: SearchCelebrityUseCase,
    val getSelectedCelebrityUseCase: GetSelectedCelebrityUseCase,
    val sendLocaleUseCase: SendLocaleUseCase,
    val setWebSettingsUseCase: SetWebSettingsUseCase
)