package com.iti.tastytrail.data.local

import androidx.room.*
import com.iti.tastytrail.data.models.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM Recipe WHERE isFavorite = 1")
    fun getFavoriteRecipes(): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe)

    @Delete
    fun deleteRecipe(recipe: Recipe)
}
