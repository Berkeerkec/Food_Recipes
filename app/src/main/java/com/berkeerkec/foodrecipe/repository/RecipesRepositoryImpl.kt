package com.berkeerkec.foodrecipe.repository

import com.berkeerkec.foodrecipe.api.FoodRecipesApi
import com.berkeerkec.foodrecipe.util.Resource
import com.berkeerkec.foodrecipes.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val api : FoodRecipesApi
): RecipesRepository {

    override suspend fun getRecipes(queries: Map<String, String>): Resource<FoodRecipe> {
        return try {
            val response = api.getRecipes(queries)

            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("Error!",null)
            }else{
                Resource.Error("Error!",null)
            }

        }catch (e : Exception){
            Resource.Error("No Data!", null)
        }
    }

}