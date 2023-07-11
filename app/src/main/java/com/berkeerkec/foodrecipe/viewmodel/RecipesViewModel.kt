package com.berkeerkec.foodrecipe.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.berkeerkec.foodrecipe.repository.LocalDataRepository
import com.berkeerkec.foodrecipe.repository.RecipesRepository
import com.berkeerkec.foodrecipe.roomdb.RecipesEntity
import com.berkeerkec.foodrecipe.util.Constant
import com.berkeerkec.foodrecipe.util.Constant.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.berkeerkec.foodrecipe.util.Constant.Companion.QUERY_API_KEY
import com.berkeerkec.foodrecipe.util.Constant.Companion.QUERY_DIET
import com.berkeerkec.foodrecipe.util.Constant.Companion.QUERY_FILL_INGREDIENTS
import com.berkeerkec.foodrecipe.util.Constant.Companion.QUERY_NUMBER
import com.berkeerkec.foodrecipe.util.Constant.Companion.QUERY_TYPE
import com.berkeerkec.foodrecipe.util.Resource
import com.berkeerkec.foodrecipes.model.FoodRecipe
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repo : RecipesRepository,
    private val localRepo : LocalDataRepository,
    application : Application
): AndroidViewModel(application) {

    private val recipes = MutableLiveData<Resource<FoodRecipe>>()
    val recipe : LiveData<Resource<FoodRecipe>>
    get() = recipes

    init {
        getRecipes(applyQueries())
    }

    val readRecipes : LiveData<List<RecipesEntity>> = localRepo.readDatabase().asLiveData()

    private fun insertRecipes(recipesEntity : RecipesEntity) = viewModelScope.launch(Dispatchers.IO) {
        localRepo.insertRecipes(recipesEntity)
    }

    private fun applyQueries() : HashMap<String,String>{
        val queries : HashMap<String,String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = Constant.API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun getRecipes(queries : Map<String,String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private fun getRecipesSafeCall(queries: Map<String, String>) {
        recipes.value = Resource.Loading()
        if (hasInternetConnection()){

            viewModelScope.launch {
                var response = repo.getRecipes(queries)
                recipes.value = response

                val foodRecipe = recipes.value!!.data

                if (foodRecipe != null){
                    offlineCacheRecipe(foodRecipe)
                }
            }
        }else{
            recipes.value = Resource.Error("No internet connection")
        }
    }

    private fun offlineCacheRecipe(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activiteManager =  connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activiteManager) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            else -> false
        }
    }
}