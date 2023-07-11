package com.berkeerkec.foodrecipe.repository

import com.berkeerkec.foodrecipe.roomdb.RecipesDao
import com.berkeerkec.foodrecipe.roomdb.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataRepositoryImpl @Inject constructor(
    private val dao : RecipesDao
) : LocalDataRepository {
    override fun readDatabase(): Flow<List<RecipesEntity>> {
        return dao.readReciipes()
    }

    override suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        dao.insertRecipes(recipesEntity)
    }
}