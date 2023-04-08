package com.pict.pbl.medswift.auth

import android.graphics.Bitmap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.pict.pbl.medswift.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class CurrentUserDetails {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val storage = Firebase.storage

    fun getUser() : User = runBlocking( Dispatchers.IO ){
        val userDocument = db.collection( "users" )
            .document( auth.currentUser!!.uid )
            .get().await()
        val user = userDocument.toObject( User::class.java )
        return@runBlocking user!!
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



}