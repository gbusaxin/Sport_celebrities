package com.example.sportcelebrities.domain.use_cases

import com.example.sportcelebrities.data.repository.Repository
import javax.inject.Inject

class GetAllCelebritiesUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.getAllCelebrities()
}