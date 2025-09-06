package com.iti.tastytrail.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.iti.tastytrail.data.dao.RecipeDao
import com.iti.tastytrail.data.dao.UserDao
import com.iti.tastytrail.data.models.Meal
import com.iti.tastytrail.data.models.User

@Database(
    entities = [Meal::class, User::class],
    version = 2,
    exportSchema = true
)
abstract class TastyTrailDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun userDao(): UserDao

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // 1. Handle meals table rename
                db.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `meals` (
                `id` TEXT PRIMARY KEY NOT NULL,
                `title` TEXT NOT NULL,
                `imageUrl` TEXT NOT NULL,
                `instructions` TEXT NOT NULL,
                `category` TEXT,
                `area` TEXT,
                `youtubeUrl` TEXT,
                `sourceUrl` TEXT,
                `tags` TEXT,
                `isFavorite` INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent()
                )

                db.execSQL(
                    """
            INSERT OR IGNORE INTO meals (id, title, imageUrl, instructions, category, area, youtubeUrl, sourceUrl, tags, isFavorite)
            SELECT id, title, imageUrl, instructions, category, area, youtubeUrl, sourceUrl, tags, isFavorite
            FROM recipes
        """.trimIndent()
                )

                db.execSQL("DROP TABLE IF EXISTS `recipes`")

                // 2. Simply drop and recreate user table with new schema
                db.execSQL("DROP TABLE IF EXISTS `user`")
                db.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `user` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `email` TEXT NOT NULL,
                `username` TEXT NOT NULL,
                `password` TEXT NOT NULL
            )
        """.trimIndent()
                )
            }
        }

        @Volatile
        private var INSTANCE: TastyTrailDatabase? = null

        fun getDatabase(context: Context): TastyTrailDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TastyTrailDatabase::class.java,
                    "tasty_trail_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
