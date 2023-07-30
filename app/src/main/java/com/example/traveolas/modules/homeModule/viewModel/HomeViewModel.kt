package com.example.traveolas.modules.homeModule.viewModel

import androidx.lifecycle.ViewModel
import com.example.traveolas.db.entites.MyTrackDetails
import com.example.traveolas.db.entites.MyTracks
import com.example.traveolas.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataManager: Repository
) : ViewModel() {

    fun addDemoData() {
        for (i in 0..10) {
            insertMyTrack()
            println("----> $i")
        }
    }

    private fun insertMyTrack() {
        val mt = MyTracks(
            null,
            "Harishchandra Pancha",
            "12th May",
            "3.011 km"
        )
//        val mtId = dataManager.insertMyTrack(mt)
//        val mtId = dataManager.insertMyTrack(mt)
//        for (i in 0..10) {
//            val mtd = MyTrackDetails(
//                null,
//                mtId.toString(),
//                i.toString(),
//                "19.4121",
//                "19.4121",
//                ""
//            )
//            dataManager.insertMyTrackDetails(mtd)
//        }
    }
}