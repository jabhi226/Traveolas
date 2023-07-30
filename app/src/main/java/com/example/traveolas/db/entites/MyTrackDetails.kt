package com.example.traveolas.db.entites

import androidx.room.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MyTracks::class,
            parentColumns = ["track_id"],
            childColumns = ["track_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["track_id"], unique = true)],
    tableName = "my_track_details"
)
class MyTrackDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "track_detail_id") var trackDetailId: Long?,
    @ColumnInfo(name = "track_id") var trackId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "latitude") var latitude: String,
    @ColumnInfo(name = "longitude") var longitude: String,
    @ColumnInfo(name = "image") var image: String,
)