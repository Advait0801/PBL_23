package com.pict.pbl.medswift.api

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pict.pbl.medswift.data.UserPrescription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class UserPrescriptions {

    private val auth = Firebase.auth

    fun getPrescriptions() : List<UserPrescription> = runBlocking( Dispatchers.IO ) {
        if( auth.currentUser?.uid != null ) {
            val database = Firebase.firestore
            val diagnosis = database
                .collection( "users" )
                .document( auth.currentUser!!.uid )
                .collection( "diagnoses" )
            val userPrescriptions = ArrayList<UserPrescription>()
            diagnosis
                .get()
                .await()
                .documents.forEach {
                    Log.e( "APP" , "Doc: ${it.id}" )
                    val prescriptions = it.reference.collection( "prescription" ).get().await()
                    if( prescriptions.documents.isNotEmpty() ) {
                        val pres = prescriptions.documents.first().toObject( UserPrescription::class.java )
                        userPrescriptions.add( pres ?: UserPrescription() )
                    }
                }
            return@runBlocking userPrescriptions
        }
        else {
            return@runBlocking emptyList()
        }
    }


}