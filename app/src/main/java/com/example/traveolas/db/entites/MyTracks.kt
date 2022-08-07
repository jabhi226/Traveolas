package com.example.traveolas.db.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_track")
class MyTracks(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "track_id") var trackId: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "distance") var distance: String,
)