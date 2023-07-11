package com.berkeerkec.foodrecipe.repository

import com.berkeerkec.foodrecipe.roomdb.RecipesEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataRepository {

    fun readDatabase() : Flow<List<RecipesEntity>>

    suspend fun insertRecipes(recipesEntity: RecipesEntity)
}