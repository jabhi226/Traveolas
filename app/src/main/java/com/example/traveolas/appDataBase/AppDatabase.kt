package com.example.traveolas.appDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.traveolas.appDataBase.AppDatabase.Companion.DATABASE_VERSION
import com.example.traveolas.appDataBase.entites.MyTracks

@Database(
    entities = [MyTracks::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "location_database"
    }
}