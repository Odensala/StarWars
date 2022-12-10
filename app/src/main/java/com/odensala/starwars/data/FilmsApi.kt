package com.odensala.starwars.data

import com.odensala.starwars.model.FilmsResponse
import retrofit2.Response
import retrofit2.http.GET

interface FilmsApi {
    @GET("films")
    suspend fun getFilms(): Response<FilmsResponse>
}