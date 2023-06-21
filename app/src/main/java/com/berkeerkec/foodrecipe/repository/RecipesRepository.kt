package com.berkeerkec.foodrecipe.repository

import com.berkeerkec.foodrecipe.util.Resource
import com.berkeerkec.foodrecipes.model.FoodRecipe

interface RecipesRepository {

    suspend fun getRecipes(queries : Map<String,String>) : Resource<FoodRecipe>
}