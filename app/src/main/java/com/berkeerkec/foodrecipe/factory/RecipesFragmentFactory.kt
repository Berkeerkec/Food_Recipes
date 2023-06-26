package com.berkeerkec.foodrecipe.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.berkeerkec.foodrecipe.adapters.RecipesAdapter
import com.berkeerkec.foodrecipe.view.RecipeFragment
import javax.inject.Inject

class RecipesFragmentFactory @Inject constructor(
    private val recipesAdapter: RecipesAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            RecipeFragment::class.java.name -> RecipeFragment(recipesAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }
}