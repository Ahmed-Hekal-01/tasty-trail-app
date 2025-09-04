package com.iti.tastytrail.repositories

import com.iti.tastytrail.data.api.ApiClient
import com.iti.tastytrail.data.database.RecipeDao
import com.iti.tastytrail.data.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    fun getFavoriteRecipes(): Flow<List<Recipe>>
    suspend fun getRecipeById(id: String): Recipe?
    suspend fun searchRecipes(query: String): List<Recipe>
    suspend fun getRandomRecipe(): Recipe?
    suspend fun getRecipesByCategory(category: String): List<Recipe>
    suspend fun toggleFavorite(recipe: Recipe)
    suspend fun saveRecipe(recipe: Recipe)
    suspend fun saveRecipes(recipes: List<Recipe>)
}

class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao,
    private val apiClient: ApiClient = ApiClient
) : RecipeRepository {

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes()
    }

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return recipeDao.getFavoriteRecipes()
    }

    override suspend fun getRecipeById(id: String): Recipe? {
        val localRecipe = recipeDao.getRecipeById(id)
        if (localRecipe != null) return localRecipe

        return try {
            apiClient.recipeApiService.getRecipeById(id).meals?.firstOrNull()?.also {
                saveRecipe(it)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun searchRecipes(query: String): List<Recipe> {
        return try {
            apiClient.recipeApiService.searchRecipes(query).meals?.also {
                saveRecipes(it)
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getRandomRecipe(): Recipe? {
        return try {
            apiClient.recipeApiService.getRandomRecipe().meals?.firstOrNull()?.also {
                saveRecipe(it)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getRecipesByCategory(category: String): List<Recipe> {
        return try {
            apiClient.recipeApiService.getRecipesByCategory(category).meals?.also {
                saveRecipes(it)
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun toggleFavorite(recipe: Recipe) {
        val updatedRecipe = recipe.copy(isFavorite = !recipe.isFavorite)
        recipeDao.updateRecipe(updatedRecipe)
    }

    override suspend fun saveRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    override suspend fun saveRecipes(recipes: List<Recipe>) {
        recipeDao.insertRecipes(recipes)
    }
}
