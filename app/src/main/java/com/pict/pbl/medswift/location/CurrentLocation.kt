package com.pict.pbl.medswift.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat

class CurrentLocation( private val context : Context ) {

    private val locationManager = context.getSystemService( Context.LOCATION_SERVICE ) as LocationManager
    private lateinit var resultFunc : ( (Location) -> Unit )

    private val locationListener = LocationListener {
        resultFunc( it )
        removeListener()
    }

    fun isLocationEnabled() : Boolean {
        return locationManager.isProviderEnabled( LocationManager.FUSED_PROVIDER )
    }

    suspend fun getCurrentLocation( result : ((Location) -> Unit) ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        resultFunc = result
        locationManager.requestLocationUpdates( LocationManager.FUSED_PROVIDER , 0L , 0f , locationListener )
    }

    private fun removeListener() {
        locationManager.removeUpdates( locationListener )
    }

}