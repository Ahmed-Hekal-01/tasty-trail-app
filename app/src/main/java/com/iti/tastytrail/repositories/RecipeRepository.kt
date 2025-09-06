package com.iti.tastytrail.repositories

import com.iti.tastytrail.data.models.Meal
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    // Local database operations
    fun getAllRecipes(): Flow<List<Meal>>
    suspend fun getRecipeById(recipeId: String): Meal?
    fun getFavoriteRecipes(): Flow<List<Meal>>
    suspend fun insertRecipe(meal: Meal)
    suspend fun insertRecipes(meals: List<Meal>)
    suspend fun updateRecipe(meal: Meal)
    suspend fun toggleFavorite(recipeId: String)
    suspend fun deleteRecipe(meal: Meal)
    suspend fun deleteRecipeById(recipeId: String)
    suspend fun clearAllRecipes()
    
    // Remote API operations
    suspend fun searchRecipesByName(name: String): Result<List<Meal>>
    suspend fun getRandomRecipe(): Result<Meal?>
    suspend fun getRecipesByCategory(category: String): Result<List<Meal>>
    suspend fun getRecipesByArea(area: String): Result<List<Meal>>
    suspend fun getRecipesByIngredient(ingredient: String): Result<List<Meal>>
    suspend fun refreshRecipes()
}
