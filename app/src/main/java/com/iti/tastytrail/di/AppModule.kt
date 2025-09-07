package com.iti.tastytrail.di
import android.content.Context
import androidx.room.Room
import com.iti.tastytrail.data.dao.RecipeDao
import com.iti.tastytrail.data.dao.UserDao
import com.iti.tastytrail.data.database.TastyTrailDatabase
import com.iti.tastytrail.data.network.MealApiService
import com.iti.tastytrail.repositories.RecipeRepository
import com.iti.tastytrail.repositories.RecipeRepositoryImpl
import com.iti.tastytrail.repositories.UserRepository
import com.iti.tastytrail.repositories.UserRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TastyTrailDatabase {
        return Room.databaseBuilder(
            context,
            TastyTrailDatabase::class.java,
            "tasty_trail_db"
        ).build()
    }

    @Provides
    fun provideUserDao(database: TastyTrailDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    fun provideRecipeDao(database: TastyTrailDatabase): RecipeDao {
        return database.recipeDao()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideMealApiService(moshi: Moshi): MealApiService {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/") // ✅ replace if different
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MealApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeDao: RecipeDao,
        apiService: MealApiService
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeDao, apiService)
    }
}

