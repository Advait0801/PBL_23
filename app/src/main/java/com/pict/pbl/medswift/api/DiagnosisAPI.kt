package com.pict.pbl.medswift.api


import android.util.Log
import com.pict.pbl.medswift.data.AnalyzeSymptom
import com.pict.pbl.medswift.data.Symptom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class DiagnosisAPI( private val client : OkHttpClient ) {

    private val baseURL = "https://api.endlessmedical.com/v1/dx/"
    private val SESSION_URL = "https://api.endlessmedical.com/v1/dx/InitSession"
    private val ACCEPT_TOU_URL = "https://api.endlessmedical.com/v1/dx/InitSession"

    private val passPhrase = "I have read, understood and I accept and agree to comply with the Terms of Use of EndlessMedicalAPI and Endless Medical services. The Terms of Use are available on endlessmedical.com"

    @kotlinx.serialization.Serializable
    private data class SessionResult(var status : String, var SessionID : String )
    @kotlinx.serialization.Serializable
    private data class StatusResult(var status : String )

    private fun getSessionId() : SessionResult {
        val request = Request.Builder().run {
            url( HttpUrl.Builder().run {
                scheme( "https" )
                host( "api.endlessmedical.com" )
                addPathSegment( "v1" )
                addPathSegment( "dx" )
                addPathSegment( "InitSession" )
                build()
            } )
            get()
            build()
        }
        val response = client.newCall( request ).execute()
        val responseJson = response.body!!.string().trim()
        Log.d( "DiagnosisAPI" , "SessionID Response: " + responseJson )
        return Json.decodeFromString( responseJson )
    }

    private fun acceptTermsOfUse(sessionID : String ) : StatusResult {
        val request = Request.Builder().run {
            url( HttpUrl.Builder().run {
                scheme( "https" )
                host( "api.endlessmedical.com" )
                addPathSegment( "v1" )
                addPathSegment( "dx" )
                addPathSegment( "AcceptTermsOfUse" )
                addQueryParameter( "SessionID" , sessionID )
                addQueryParameter( "passphrase" , passPhrase )
                build()
            } )
            post( ByteArray(0).toRequestBody() )
            build()
        }
        val response = client.newCall( request ).execute()
        val responseJson = response.body!!.string().trim()
        Log.d( "DiagnosisAPI" , "AcceptTOU Url: " + request.url )
        Log.d( "DiagnosisAPI" , "AcceptTOU Response: " + responseJson )
        return Json.decodeFromString( responseJson )
    }

    private fun uploadSymptom( analyzeSymptom: AnalyzeSymptom , sessionID: String ) : StatusResult {
        val request = Request.Builder().run {
            url( HttpUrl.Builder().run {
                scheme( "https" )
                host( "api.endlessmedical.com" )
                addPathSegment( "v1" )
                addPathSegment( "dx" )
                addPathSegment( "UpdateFeature" )
                addQueryParameter( "SessionID" , sessionID )
                addQueryParameter( "name" , analyzeSymptom.name )
                addQueryParameter( "value" , analyzeSymptom.value )
                build()
            } )
            post( ByteArray(0).toRequestBody() )
            build()
        }
        val response = client.newCall( request ).execute()
        val responseJson = response.body!!.string().trim()
        Log.d( "DiagnosisAPI" , "UpdateFeature Url: " + request.url )
        Log.d( "DiagnosisAPI" , "UpdateFeature Response: " + responseJson )
        return Json.decodeFromString( responseJson )
    }

    fun getAnalysis( symptoms : List<AnalyzeSymptom> = ArrayList() ) {
        CoroutineScope( Dispatchers.IO ).launch {
            val sessionResponse = getSessionId()
            if( sessionResponse.status.equals( "ok" ) ) {
                val sessionID = sessionResponse.SessionID
                val acceptTOUStatus = acceptTermsOfUse( sessionID )
                if( acceptTOUStatus.status == "ok" ) {
                    for( symptom in symptoms ) {
                        uploadSymptom( symptom , sessionID )
                    }

                }
            }
            else {
                Log.d( "DiagnosisAPI" , "Session status not ok" )
            }
        }
    }
    
}

