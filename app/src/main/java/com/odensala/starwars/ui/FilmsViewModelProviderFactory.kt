package com.odensala.starwars.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.odensala.starwars.repository.FilmsRepository

class FilmsViewModelProviderFactory(private val filmsRepository: FilmsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilmsViewModel(filmsRepository) as T
    }
}