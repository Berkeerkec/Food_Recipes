package com.berkeerkec.foodrecipe.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import com.berkeerkec.foodrecipe.util.Constant.Companion.DEFAULT_DIET_TYPE
import com.berkeerkec.foodrecipe.util.Constant.Companion.DEFAULT_MEAL_TYPE
import com.berkeerkec.foodrecipe.util.Constant.Companion.PREFERENCES_DIET_TYPE
import com.berkeerkec.foodrecipe.util.Constant.Companion.PREFERENCES_DIET_TYPE_ID
import com.berkeerkec.foodrecipe.util.Constant.Companion.PREFERENCES_MEAL_TYPE
import com.berkeerkec.foodrecipe.util.Constant.Companion.PREFERENCES_MEAL_TYPE_ID
import com.berkeerkec.foodrecipe.util.Constant.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)
@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context : Context
) {

    private object PreferenceKeys{
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    private val dataStore : DataStore<Preferences> = context.dataStore

    suspend fun saveMealAndDietType(mealType : String, mealTypeId : Int, dietType : String, dietTypeId : Int){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    val readMealAndDietType : Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
    }.map {preference ->
            val selectedMealType = preference[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preference[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preference[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preference[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

}

data class MealAndDietType(
    val selectedMealType : String,
    val selectedMealTypeId : Int,
    val selectedDietType : String,
    val selectedDietTypeId : Int,
)