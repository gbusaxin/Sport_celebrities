package com.example.sportcelebrities.data.remote

import com.example.sportcelebrities.domain.model.Celebrity
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/celebrities/celebrity.php")
    suspend fun getAllCelebrity(
        @Query("page") page: Int = 1
    ): List<Celebrity>

}