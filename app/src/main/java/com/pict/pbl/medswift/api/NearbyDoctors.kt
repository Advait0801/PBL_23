package com.pict.pbl.medswift.api

import android.content.Context
import android.location.Geocoder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pict.pbl.medswift.data.Doctor
import com.pict.pbl.medswift.data.UserPrescription
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Locale

class NearbyDoctors{

    private val auth = Firebase.auth
    private val tolerance = 0.02

    fun getNearbyDoctors( userLat : Float , userLng : Float ) : Deferred<ArrayList<Doctor>> = CoroutineScope( Dispatchers.IO ).async {
        if( auth.currentUser?.uid != null ) {

            val database = Firebase.firestore
            val doctors = database.collection( "doctors" )

            val upperGeoPoint = GeoPoint( userLat + tolerance , userLng + tolerance )
            val lowerGeoPoint = GeoPoint( userLat - tolerance , userLng - tolerance )

            val query = doctors
                .whereLessThan( "location" , upperGeoPoint )
                .whereGreaterThan( "location" , lowerGeoPoint )

            val nearbyDoctors = ArrayList<Doctor>()
            query.get()
                .await()
                .documents.forEach{ it ->
                    nearbyDoctors.add( it.toObject( Doctor::class.java )!! )
                }
            nearbyDoctors.filter{ it ->
                it.longitude < userLng + tolerance && it.longitude > userLng - tolerance
            }

            return@async nearbyDoctors
        }
        else {
            return@async ArrayList<Doctor>()
        }
    }


}