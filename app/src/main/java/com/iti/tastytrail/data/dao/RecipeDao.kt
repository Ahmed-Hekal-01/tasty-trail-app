package com.iti.tastytrail.data.dao

import androidx.room.*
import com.iti.tastytrail.data.models.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<Recipe>>
    
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: String): Recipe?
    
    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    fun getFavoriteRecipes(): Flow<List<Recipe>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)
    
    @Update
    suspend fun updateRecipe(recipe: Recipe)
    
    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE id = :recipeId")
    suspend fun updateFavoriteStatus(recipeId: String, isFavorite: Boolean)
    
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
    
    @Query("DELETE FROM recipes WHERE id = :recipeId")
    suspend fun deleteRecipeById(recipeId: String)
    
    @Query("DELETE FROM recipes")
    suspend fun clearAllRecipes()
}
