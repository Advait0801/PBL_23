package com.pict.pbl.medswift.api

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pict.pbl.medswift.data.UserPrescription
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.logging.ErrorManager

class UserPrescriptions {

    private val auth = Firebase.auth

    fun getPrescriptions(
        resultCallback : ( (List<UserPrescription>) -> Unit ) ,
        errorCallback : ( (String) -> Unit )
    ) = CoroutineScope( Dispatchers.IO ).launch {
        try {
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
                        val prescriptions = it.reference.collection( "prescription" ).get().await()
                        if( prescriptions.documents.isNotEmpty() ) {
                            val pres = prescriptions.documents.first().toObject( UserPrescription::class.java )
                            userPrescriptions.add( pres ?: UserPrescription() )
                        }
                    }
                if( userPrescriptions.isEmpty() ) {
                    withContext( Dispatchers.Main ) {
                        errorCallback( ErrorMessages.PRESCRIPTION_NOT_FOUND.message )
                    }
                }
                else {
                    withContext( Dispatchers.Main ) {
                        resultCallback( userPrescriptions )
                    }
                }
            }
            else {
                withContext( Dispatchers.Main ){
                    errorCallback( ErrorMessages.AUTH_USER_ERROR.message )
                }
            }
        }
        catch( e : Exception ) {
            withContext( Dispatchers.Main ) {
                errorCallback( e.message ?: ErrorMessages.UNKNOWN.message )
            }
        }
    }


}