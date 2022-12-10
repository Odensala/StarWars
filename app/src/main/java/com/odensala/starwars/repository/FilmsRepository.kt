package com.odensala.starwars.repository

import com.odensala.starwars.data.RetrofitInstance

class FilmsRepository {
    suspend fun getFilms() =
        RetrofitInstance.api.getFilms()
}