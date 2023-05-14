package com.pict.pbl.medswift.api

enum class ErrorMessages( val message : String ) {

    UNKNOWN( "An error occurred while performing the requested operation." ) ,
    AUTH_USER_ERROR( "User is not authenticated" ) ,

    FIREBASE_NOT_CONNECTED( "Unable to connect with the database. Please check your internet connection" ) ,

    USER_DETAILS_NOT_FOUND( "User details were not found." ) ,

    PRESCRIPTION_NOT_FOUND( "Unable to load prescriptions" ) ,

    DIAGNOSIS_NOT_FOUND( "Diagnosis not found" )

}