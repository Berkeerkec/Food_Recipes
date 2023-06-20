package com.berkeerkec.foodrecipe.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.berkeerkec.foodrecipe.R
import com.berkeerkec.foodrecipe.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.recipesFoodFragment ->{
                    navController.navigate(R.id.recipeFragment)
                    true
                }
                R.id.favoriteRecipesFragment ->{
                    navController.navigate(R.id.favoriteFragment)
                    true
                }
                R.id.foodJokeFragment ->{
                    navController.navigate(R.id.jokeFragment)
                    true
                }
                else -> false

            }
        }

    }
}