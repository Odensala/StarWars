package com.odensala.starwars.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odensala.starwars.model.FilmsResponse
import com.odensala.starwars.repository.FilmsRepository
import com.odensala.starwars.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class FilmsViewModel(private val filmsRepository: FilmsRepository) : ViewModel() {

    val films: MutableLiveData<Resource<FilmsResponse>> = MutableLiveData()

    init {
        getFilms()
    }

    // Stays alive as long as our ViewModel is alive
    private fun getFilms() = viewModelScope.launch {
        films.postValue(Resource.Loading())
        val response = filmsRepository.getFilms()
        films.postValue(handleFilmsResponse(response))
    }

    private fun handleFilmsResponse(response: Response<FilmsResponse>): Resource<FilmsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { responseResult ->
                return Resource.Success(responseResult)
            }
        }
        return Resource.Error(response.message())
    }
}