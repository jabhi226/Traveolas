package com.example.traveolas.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.traveolas.db.entites.MyTrackDetails

@Dao
interface MyTrackDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyTrackDetails(mtd: MyTrackDetails): Long
}