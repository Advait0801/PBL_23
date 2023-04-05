package com.pict.pbl.medswift.auth

import android.graphics.Bitmap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pict.pbl.medswift.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class CurrentUser {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

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

    fun uploadImage( image : Bitmap) {
        // TODO: Upload profile image to Storage here
    }

    fun getUserImage() : Bitmap? {
        // TODO: Fetch user image
        return null
    }



}