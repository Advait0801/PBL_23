package com.pict.pbl.medswift.login

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pict.pbl.medswift.data.User
import com.pict.pbl.medswift.viewmodels.LoginViewModel
import java.util.regex.Pattern

class LoginManager( private val loginViewModel: LoginViewModel? = null ) {

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

    fun loginUser( emailAddress: String , password: String , result : ( () -> Unit ) ) {
        firebaseAuth.signInWithEmailAndPassword( emailAddress , password )
            .addOnSuccessListener {
                result()
            }
            .addOnFailureListener {
                if( loginViewModel != null ) {
                    loginViewModel.errorMessage.value = it.message
                    loginViewModel.errorMessageFlag.value = true
                }
            }
    }


    fun createUser( user: User , password: String ) {
        firebaseAuth.createUserWithEmailAndPassword( user.email , password )
            .addOnSuccessListener {
                // TODO: Send verification email and create user in DB
            }
            .addOnFailureListener {
                if( loginViewModel != null ) {
                    loginViewModel.errorMessage.value = it.message
                    loginViewModel.errorMessageFlag.value = true
                }
            }
    }

    fun validateUser( emailAddress : String , password : String ) {
        firebaseAuth.signInWithEmailAndPassword( emailAddress , password )
            .addOnSuccessListener {
                firebaseAuth.currentUser?.sendEmailVerification()
                // TODO: Go to home screen
            }
            .addOnFailureListener {
                // TODO: Handle user signin exception here
            }
    }

}