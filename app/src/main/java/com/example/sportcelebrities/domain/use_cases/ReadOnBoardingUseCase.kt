package com.example.sportcelebrities.domain.use_cases

import com.example.sportcelebrities.data.repository.Repository

class ReadOnBoardingUseCase(
    private val repository: Repository
) {
    operator fun invoke() = repository.readOnBoardingState()
}