package com.iti.tastytrail.repositories

import com.iti.tastytrail.data.dao.RecipeDao
import com.iti.tastytrail.data.models.Recipe
import com.iti.tastytrail.data.network.MealApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao,
    private val apiService: MealApiService
) : RecipeRepository {
    
    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes()
    }
    
    override suspend fun getRecipeById(recipeId: String): Recipe? {
        return recipeDao.getRecipeById(recipeId)
    }
    
    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return recipeDao.getFavoriteRecipes()
    }
    
    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }
    
    override suspend fun insertRecipes(recipes: List<Recipe>) {
        recipeDao.insertRecipes(recipes)
    }
    
    override suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }
    
    override suspend fun toggleFavorite(recipeId: String) {
        val recipe = recipeDao.getRecipeById(recipeId)
        recipe?.let {
            recipeDao.updateFavoriteStatus(recipeId, !it.isFavorite)
        }
    }
    
    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
    
    override suspend fun deleteRecipeById(recipeId: String) {
        recipeDao.deleteRecipeById(recipeId)
    }
    
    override suspend fun clearAllRecipes() {
        recipeDao.clearAllRecipes()
    }
    
    // Remote API operations
    override suspend fun searchRecipesByName(name: String): Result<List<Recipe>> {
        return try {
            val response = apiService.searchMealsByName(name)
            if (response.isSuccessful && response.body() != null) {
                val recipes = response.body()?.meals ?: emptyList()
                Result.success(recipes)
            } else {
                Result.failure(Exception("Failed to search recipes: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getRandomRecipe(): Result<Recipe?> {
        return try {
            val response = apiService.getRandomMeal()
            if (response.isSuccessful && response.body() != null) {
                val recipe = response.body()?.meals?.firstOrNull()
                Result.success(recipe)
            } else {
                Result.failure(Exception("Failed to get random recipe: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getRecipesByCategory(category: String): Result<List<Recipe>> {
        return try {
            val response = apiService.getMealsByCategory(category)
            if (response.isSuccessful && response.body() != null) {
                val recipes = response.body()?.meals ?: emptyList()
                Result.success(recipes)
            } else {
                Result.failure(Exception("Failed to get recipes by category: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getRecipesByArea(area: String): Result<List<Recipe>> {
        return try {
            val response = apiService.getMealsByArea(area)
            if (response.isSuccessful && response.body() != null) {
                val recipes = response.body()?.meals ?: emptyList()
                Result.success(recipes)
            } else {
                Result.failure(Exception("Failed to get recipes by area: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getRecipesByIngredient(ingredient: String): Result<List<Recipe>> {
        return try {
            val response = apiService.getMealsByIngredient(ingredient)
            if (response.isSuccessful && response.body() != null) {
                val recipes = response.body()?.meals ?: emptyList()
                Result.success(recipes)
            } else {
                Result.failure(Exception("Failed to get recipes by ingredient: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun refreshRecipes() {
        try {
            // Get some popular recipes and cache them locally
            val randomRecipeResult = getRandomRecipe()
            randomRecipeResult.getOrNull()?.let { recipe ->
                insertRecipe(recipe)
            }
        } catch (e: Exception) {
            // Handle refresh error silently or log it
        }
    }
}
