package com.iti.tastytrail.data.remote

import com.iti.tastytrail.data.models.Recipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("search.php")
    suspend fun searchRecipes(@Query("s") query: String): List<Recipe>

    @GET("lookup.php")
    suspend fun getRecipeDetail(@Query("i") id: String): Recipe
}
