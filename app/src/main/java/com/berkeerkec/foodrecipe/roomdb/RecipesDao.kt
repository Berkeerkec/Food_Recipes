package com.berkeerkec.foodrecipe.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity : RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readReciipes() : Flow<List<RecipesEntity>>
}