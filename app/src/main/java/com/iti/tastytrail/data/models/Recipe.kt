package com.iti.tastytrail.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey
    @SerializedName("idMeal")
    val id: String,

    @SerializedName("strMeal")
    val title: String,

    @SerializedName("strMealThumb")
    val imageUrl: String,

    @SerializedName("strInstructions")
    val instructions: String,

    @SerializedName("strCategory")
    val category: String? = null,

    @SerializedName("strArea")
    val area: String? = null,

    var isFavorite: Boolean = false
)
