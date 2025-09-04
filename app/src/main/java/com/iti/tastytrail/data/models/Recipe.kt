package com.iti.tastytrail.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey
    @Json(name = "idMeal")
    val id: String,
    @Json(name = "strMeal")
    val title: String,
    @Json(name = "strMealThumb")
    val imageUrl: String,
    @Json(name = "strInstructions")
    val instructions: String,
    @Json(name = "strCategory")
    val category: String? = null,
    @Json(name = "strArea")
    val area: String? = null,
    @Json(name = "strYoutube")
    val youtubeUrl: String? = null,
    @Json(name = "strSource")
    val sourceUrl: String? = null,
    @Json(name = "strTags")
    val tags: String? = null,
    val isFavorite: Boolean = false
)
