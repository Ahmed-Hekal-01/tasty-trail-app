package com.iti.tastytrail.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iti.tastytrail.data.models.Recipe

@Database(entities = [Recipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}
