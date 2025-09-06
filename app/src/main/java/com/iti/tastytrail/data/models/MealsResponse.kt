package com.iti.tastytrail.data.models

import com.squareup.moshi.Json

data class MealsResponse(
    @Json(name = "meals")
    val meals: List<Meal>?
)
