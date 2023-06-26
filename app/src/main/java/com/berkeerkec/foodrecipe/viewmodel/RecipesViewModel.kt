package com.berkeerkec.foodrecipe.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.berkeerkec.foodrecipe.repository.RecipesRepository
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
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repo : RecipesRepository,
    application : Application
): AndroidViewModel(application) {

    private val recipes = MutableLiveData<Resource<FoodRecipe>>()
    val recipe : LiveData<Resource<FoodRecipe>>
    get() = recipes

    init {
        getRecipes(applyQueries())
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
            }
        }else{
            recipes.value = Resource.Error("No internet connection")
        }
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