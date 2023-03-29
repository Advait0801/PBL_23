package com.pict.pbl.medswift.api

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

}