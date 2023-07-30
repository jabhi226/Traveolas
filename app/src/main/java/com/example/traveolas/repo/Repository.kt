package com.example.traveolas.repo

import com.example.traveolas.db.daos.MyTrackDetailsDao
import com.example.traveolas.db.daos.MyTracksDao
import com.example.traveolas.db.entites.MyTrackDetails
import com.example.traveolas.db.entites.MyTracks
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val myTracksDao: MyTracksDao,
    private val myTrackDetailsDao: MyTrackDetailsDao
) {

    fun insertMyTrack(myTrack: MyTracks): Long {
        return myTracksDao.insertMyTrack(myTrack)
    }

    fun insertMyTrackDetails(mtd: MyTrackDetails) {
        myTrackDetailsDao.insertMyTrackDetails(mtd)
    }
}