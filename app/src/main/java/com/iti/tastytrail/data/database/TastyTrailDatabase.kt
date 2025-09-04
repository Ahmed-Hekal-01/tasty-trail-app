package com.iti.tastytrail.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.iti.tastytrail.data.dao.RecipeDao
import com.iti.tastytrail.data.dao.UserDao
import com.iti.tastytrail.data.models.Recipe
import com.iti.tastytrail.data.models.User

@Database(
    entities = [Recipe::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class TastyTrailDatabase : RoomDatabase() {
    
    abstract fun recipeDao(): RecipeDao
    abstract fun userDao(): UserDao
    
    companion object {
        @Volatile
        private var INSTANCE: TastyTrailDatabase? = null
        
        fun getDatabase(context: Context): TastyTrailDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TastyTrailDatabase::class.java,
                    "tasty_trail_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
