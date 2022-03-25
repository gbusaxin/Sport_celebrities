package com.example.sportcelebrities.domain.repository

import com.example.sportcelebrities.domain.model.Celebrity

interface LocalDataSource {
    suspend fun getSelectedCelebrity(id:Int):Celebrity
}