package com.berkeerkec.foodrecipe.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berkeerkec.foodrecipe.util.Constant.Companion.RECIPES_TABLE
import com.berkeerkec.foodrecipes.model.FoodRecipe

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe : FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}