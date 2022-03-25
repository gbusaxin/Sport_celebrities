package com.example.sportcelebrities.data.remote

import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.domain.model.RequestDto
import com.example.sportcelebrities.domain.model.ResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("/celebrities/celebrity.php")
    suspend fun getAllCelebrity(
        @Query("page") page: Int = 1
    ): List<Celebrity>

    @POST("/celebrities/splash.php")
    suspend fun sendLocale(@Body locale: RequestDto): ResponseDto

}