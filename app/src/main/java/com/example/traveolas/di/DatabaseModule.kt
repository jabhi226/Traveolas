package com.example.traveolas.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.traveolas.db.AppDatabase
import com.example.traveolas.db.daos.MyTracksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
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
            .addCallback(roomCallback)
            .build()
    }

    private val roomCallback: RoomDatabase.Callback = object : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            db.execSQL("insert into my_track values (1, '12th May', 'Harishchandra Pancha', '3.011 km')")
            db.execSQL("insert into my_track values (2, '12th May', 'Harishchandra Pancha', '3.011 km')")
            db.execSQL("insert into my_track values (3, '12th May', 'Harishchandra Pancha', '3.011 km')")
            db.execSQL("insert into my_track values (4, '12th May', 'Harishchandra Pancha', '3.011 km')")
            db.execSQL("insert into my_track values (5, '12th May', 'Harishchandra Pancha', '3.011 km')")
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
        }
    }

    @Provides
    fun provideMyTrackDao(db :AppDatabase): MyTracksDao{
        return db.myTrackDao
    }
}