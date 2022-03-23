package com.example.sportcelebrities.domain.use_cases

import com.example.sportcelebrities.data.repository.Repository

class SearchCelebrityUseCase(
    private val repository: Repository
) {
    operator fun invoke(query: String) = repository.searchCelebrity(query = query)
}