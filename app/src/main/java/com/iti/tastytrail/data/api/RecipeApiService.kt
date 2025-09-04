package com.iti.tastytrail.data.api

import com.iti.tastytrail.data.models.Recipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("search.php")
    suspend fun searchRecipes(@Query("s") query: String): MealResponse

    @GET("lookup.php")
    suspend fun getRecipeById(@Query("i") id: String): MealResponse

    @GET("random.php")
    suspend fun getRandomRecipe(): MealResponse

    @GET("filter.php")
    suspend fun getRecipesByCategory(@Query("c") category: String): MealResponse
}

data class MealResponse(
    val meals: List<Recipe>?
)
