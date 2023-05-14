package com.pict.pbl.medswift.auth

import android.graphics.Bitmap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.pict.pbl.medswift.api.ErrorMessages
import com.pict.pbl.medswift.data.AnalyzeSymptom
import com.pict.pbl.medswift.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.UUID

class CurrentUserDetails {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val storage = Firebase.storage

    fun getUser(
        resultCallback : ( (User) -> Unit ) ,
        errorCallback : ( (String) -> Unit )
    ) = CoroutineScope( Dispatchers.IO ).launch{
        try {
            val userDocument = db.collection( "users" )
                .document( auth.currentUser!!.uid )
                .get().await()
            val user = userDocument.toObject( User::class.java )
            if( user != null ) {
                withContext( Dispatchers.Main ) {
                    resultCallback( user )
                }
            }
            else {
                withContext( Dispatchers.Main ) {
                    errorCallback( ErrorMessages.USER_DETAILS_NOT_FOUND.message )
                }
            }
        }
        catch( e : Exception ) {
            withContext( Dispatchers.Main ) {
                errorCallback( e.message ?: ErrorMessages.UNKNOWN.message )
            }
        }
    }

    fun isUserAuthenticated() : Boolean {
        return auth.currentUser == null
    }

    fun createUser( user : User , uid : String ) = runBlocking( Dispatchers.IO ){
        db.collection( "users" )
            .document( uid )
            .set( user )
    }

    suspend fun uploadImage( image : Bitmap ) = CoroutineScope( Dispatchers.IO ).launch {
        val baos = ByteArrayOutputStream()
        image.compress( Bitmap.CompressFormat.JPEG , 100 , baos )
        val result = storage.getReference( "profilePictures/${auth.currentUser?.uid}.jpg" )
            .putBytes( baos.toByteArray() ).await()
    }

    fun getUserImage() : String = runBlocking( Dispatchers.IO ){
        val url = storage.getReference( "profilePictures/${auth.currentUser?.uid}.jpg" ).downloadUrl.await()
        return@runBlocking url.toString()
    }

    suspend fun uploadDiagnosis( symptoms : List<AnalyzeSymptom> , predictions : HashMap<String,Float> ) =
        CoroutineScope( Dispatchers.IO ).launch {
            val symptomsCollection = db.collection( "users" )
                .document( auth.currentUser!!.uid )
                .collection( "diagnosis" )
                .document( UUID.randomUUID().toString() )
                .collection( "symptoms" )

            for ( symptom in symptoms ) {
                symptomsCollection
                    .document( UUID.randomUUID().toString() )
                    .set( symptom )
            }
            val predictionsCollection = db.collection( "users" )
                .document( auth.currentUser!!.uid )
                .collection( "diagnosis" )
                .document( UUID.randomUUID().toString() )
                .collection( "predictions" )
            for( prediction in predictions ) {
                predictionsCollection
                    .document( UUID.randomUUID().toString() )
                    .set( mapOf( "name" to prediction.key , "value" to prediction.value ) )
            }
    }



}