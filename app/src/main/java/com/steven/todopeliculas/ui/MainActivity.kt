package com.steven.todopeliculas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.steven.todopeliculas.R
import com.steven.todopeliculas.databinding.ActivityMainBinding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.steven.todopeliculas.core.hide
import com.steven.todopeliculas.core.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.navigation_container)
        NavigationUI.setupWithNavController(binding.btnNavigation, navController)
        observeDestinationChange()
    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.loginFragment -> binding.btnNavigation.hide()
                R.id.registerFragment -> binding.btnNavigation.hide()
                else -> binding.btnNavigation.show()
            }
        }
    }

   override fun onBackPressed() {
        val id = navController.currentDestination?.id
        if (id != R.id.movieFragment) {
            binding.btnNavigation.selectedItemId = R.id.movieFragment
        } else {
            super.onBackPressed()
            finish()
        }
    }
}