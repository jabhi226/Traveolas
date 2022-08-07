package com.example.traveolas.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.traveolas.db.entites.MyTracks

@Dao
interface MyTracksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyTrack(mt: MyTracks): Long

    @Query("SELECT * FROM my_track")
    fun getAllMyTracks(): List<MyTracks>
}