package com.example.traveolas.modules.homeModule.viewModel


import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.traveolas.utils.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(locationHelper: LocationHelper) : ViewModel() {

    val location = MutableLiveData<Location?>()

    init {
        location.value = locationHelper.location
    }

    fun updateLocation(locationHelper: LocationHelper){
//        location.
    }


}