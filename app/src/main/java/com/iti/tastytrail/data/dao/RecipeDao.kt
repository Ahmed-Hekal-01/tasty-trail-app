package com.iti.tastytrail.data.dao

import androidx.room.*
import com.iti.tastytrail.data.models.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    
    @Query("SELECT * FROM meals")
    fun getAllRecipes(): Flow<List<Meal>>
    
    @Query("SELECT * FROM meals WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: String): Meal?
    
    @Query("SELECT * FROM meals WHERE isFavorite = 1")
    fun getFavoriteRecipes(): Flow<List<Meal>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(meal: Meal)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(meals: List<Meal>)
    
    @Update
    suspend fun updateRecipe(meal: Meal)
    
    @Query("UPDATE meals SET isFavorite = :isFavorite WHERE id = :recipeId")
    suspend fun updateFavoriteStatus(recipeId: String, isFavorite: Boolean)
    
    @Delete
    suspend fun deleteRecipe(meal: Meal)
    
    @Query("DELETE FROM MEALS WHERE id = :recipeId")
    suspend fun deleteRecipeById(recipeId: String)
    
    @Query("DELETE FROM meals")
    suspend fun clearAllRecipes()
}
