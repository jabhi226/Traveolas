package com.example.traveolas.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.traveolas.db.AppDatabase
import com.example.traveolas.db.daos.MyTrackDetailsDao
import com.example.traveolas.db.daos.MyTracksDao
import com.example.traveolas.utils.LocationHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMyTrackDao(db: AppDatabase): MyTracksDao {
        return db.myTrackDao
    }

    @Provides
    fun provideMyTrackDetailsDao(db: AppDatabase): MyTrackDetailsDao {
        return db.myTrackDetailsDao
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context{
        return context
    }

    @Provides
    @Singleton
    fun provideLocationHelper(@ApplicationContext context: Context): LocationHelper{
        return LocationHelper(context)
    }
}