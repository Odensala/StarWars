package com.odensala.starwars

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.odensala.starwars.repository.FilmsRepository
import com.odensala.starwars.ui.FilmsViewModel
import com.odensala.starwars.ui.FilmsViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    lateinit var filmsViewModel: FilmsViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filmsRepository = FilmsRepository()
        val filmsViewModelFactory = FilmsViewModelProviderFactory(filmsRepository)
        filmsViewModel = ViewModelProvider(this, filmsViewModelFactory)[FilmsViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    /**
     * Enables back button navigation
     */
    override fun onSupportNavigateUp(): Boolean {
        navController = this.findNavController(R.id.nav_host_fragment_container)
        return navController.navigateUp()
    }
}