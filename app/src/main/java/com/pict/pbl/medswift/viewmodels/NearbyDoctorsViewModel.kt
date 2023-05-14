package com.pict.pbl.medswift.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.pict.pbl.medswift.data.Doctor

class NearbyDoctorsViewModel : ViewModel() {

    val clickedNearbyDoctor = MutableLiveData( Doctor() )
    var nearbyDoctorsNavController : NavController? = null


}