package com.iti.tastytrail.data.network

import com.iti.tastytrail.data.models.MealsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    
    @GET("search.php")
    suspend fun searchMealsByName(
        @Query("s") mealName: String
    ): Response<MealsResponse>
    
    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") mealId: String
    ): Response<MealsResponse>
    
    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealsResponse>
    
    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): Response<MealsResponse>
    
    @GET("filter.php")
    suspend fun getMealsByArea(
        @Query("a") area: String
    ): Response<MealsResponse>
    
    @GET("filter.php")
    suspend fun getMealsByIngredient(
        @Query("i") ingredient: String
    ): Response<MealsResponse>
    
    @GET("categories.php")
    suspend fun getCategories(): Response<MealsResponse>
    
    @GET("list.php")
    suspend fun getAreas(
        @Query("a") list: String = "list"
    ): Response<MealsResponse>
    
    @GET("list.php")
    suspend fun getIngredients(
        @Query("i") list: String = "list"
    ): Response<MealsResponse>
}
