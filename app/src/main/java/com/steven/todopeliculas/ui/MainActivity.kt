package com.steven.todopeliculas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.steven.todopeliculas.R
import com.steven.todopeliculas.databinding.ActivityMainBinding
import android.view.WindowManager




class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = Navigation.findNavController(this, R.id.navigation_container)
        NavigationUI.setupWithNavController(binding.btnNavigation, navController)
    }
}