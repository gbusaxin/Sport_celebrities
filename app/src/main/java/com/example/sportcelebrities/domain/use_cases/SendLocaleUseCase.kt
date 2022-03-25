package com.example.sportcelebrities.domain.use_cases

import com.example.sportcelebrities.data.repository.Repository

class SendLocaleUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.sendLocale()
}