package com.example.traveolas.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.traveolas.db.AppDatabase.Companion.DATABASE_VERSION
import com.example.traveolas.db.daos.MyTrackDetailsDao
import com.example.traveolas.db.daos.MyTracksDao
import com.example.traveolas.db.entites.MyTrackDetails
import com.example.traveolas.db.entites.MyTracks

@Database(
    entities = [MyTracks::class, MyTrackDetails::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "location_database"
    }

    abstract val myTrackDao: MyTracksDao
    abstract val myTrackDetailsDao: MyTrackDetailsDao
}