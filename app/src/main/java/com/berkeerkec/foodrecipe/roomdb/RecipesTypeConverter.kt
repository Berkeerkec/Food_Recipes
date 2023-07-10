package com.berkeerkec.foodrecipe.roomdb

import androidx.room.TypeConverter
import com.berkeerkec.foodrecipes.model.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipesToString(foodRecipe: FoodRecipe) : String{
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data : String) : FoodRecipe{
        val listType = object :TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data,listType)
    }
}
