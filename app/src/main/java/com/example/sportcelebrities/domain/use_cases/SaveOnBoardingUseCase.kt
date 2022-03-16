package com.example.sportcelebrities.domain.use_cases

import com.example.sportcelebrities.data.repository.Repository

class SaveOnBoardingUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(completed: Boolean) =
        repository.saveOnBoardingState(completed = completed)
}