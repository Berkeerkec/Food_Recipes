package com.berkeerkec.foodrecipe.util

class Constant {
    companion object{
        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "API_KEY"

        //API Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room Database
        const val DATA_BASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
    }
}