package com.berkeerkec.foodrecipe.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berkeerkec.foodrecipes.model.FoodRecipe

@Entity(tableName = "recipes_table")
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {

    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}