package com.iti.tastytrail.repositories

import com.iti.tastytrail.data.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    // Local database operations
    fun getAllRecipes(): Flow<List<Recipe>>
    suspend fun getRecipeById(recipeId: String): Recipe?
    fun getFavoriteRecipes(): Flow<List<Recipe>>
    suspend fun insertRecipe(recipe: Recipe)
    suspend fun insertRecipes(recipes: List<Recipe>)
    suspend fun updateRecipe(recipe: Recipe)
    suspend fun toggleFavorite(recipeId: String)
    suspend fun deleteRecipe(recipe: Recipe)
    suspend fun deleteRecipeById(recipeId: String)
    suspend fun clearAllRecipes()
    
    // Remote API operations
    suspend fun searchRecipesByName(name: String): Result<List<Recipe>>
    suspend fun getRandomRecipe(): Result<Recipe?>
    suspend fun getRecipesByCategory(category: String): Result<List<Recipe>>
    suspend fun getRecipesByArea(area: String): Result<List<Recipe>>
    suspend fun getRecipesByIngredient(ingredient: String): Result<List<Recipe>>
    suspend fun refreshRecipes()
}
