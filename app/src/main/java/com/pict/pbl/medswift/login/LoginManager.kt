package com.pict.pbl.medswift.login

import java.util.regex.Pattern

class LoginManager {
    companion object {
        private val emailRegex = Pattern.compile( "[a-zA-Z0-9.]+@[a-zA-Z]+\\.com" )

        fun checkEmailAddress( emailAddress: String ) : Boolean {
            // TODO: Check if email address is well-formed, use RegEx
            return emailRegex.matcher(emailAddress).matches()
        }
    }

    fun validateUser( emailAddress : String , password : String ) {
        // TODO: Add user validation here
    }

}