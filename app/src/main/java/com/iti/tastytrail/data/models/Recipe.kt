package com.iti.tastytrail.data.models

data class Recipe(
    val id: String,
    val title: String,
    val imageUrl: String,
    val instructions: String,
    val isFavorite: Boolean = false
)
