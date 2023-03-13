package com.pict.pbl.medswift.login

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class LoginManager {

    private val firebaseAuth = Firebase.auth

    companion object {

        // TODO: Update regex for space
        private val emailRegex = Pattern.compile( "[a-zA-Z0-9.]+@[a-zA-Z]+\\.com" )

        fun checkEmailAddress( emailAddress: String ) : Boolean {
            return emailRegex.matcher(emailAddress).matches()
        }

        fun isUserLoggedIn() : Boolean {
            return Firebase.auth.currentUser == null
        }

    }

    fun createUser( emailAddress: String , password: String ) {
        firebaseAuth.createUserWithEmailAndPassword( emailAddress , password )
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                // TODO: Handle user creation exception here
            }
    }

    fun validateUser( emailAddress : String , password : String ) {
        firebaseAuth.signInWithEmailAndPassword( emailAddress , password )
            .addOnSuccessListener {
                // TODO: Go to home screen
            }
            .addOnFailureListener {
                // TODO: Handle user signin exception here
            }
    }

}