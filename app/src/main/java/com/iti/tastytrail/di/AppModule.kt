package com.iti.tastytrail.di

import android.content.Context
import androidx.room.Room
import com.iti.tastytrail.data.dao.UserDao
import com.iti.tastytrail.data.database.TastyTrailDatabase
import com.iti.tastytrail.repositories.UserRepository
import com.iti.tastytrail.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
}
