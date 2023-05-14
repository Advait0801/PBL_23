package com.pict.pbl.medswift.api


import android.util.Log
import com.pict.pbl.medswift.data.AnalyzeSymptom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class DiagnosisAPI( private val client : OkHttpClient ) {

    private val passPhrase = "I have read, understood and I accept and agree to comply with the Terms of Use of EndlessMedicalAPI and Endless Medical services. The Terms of Use are available on endlessmedical.com"

    @kotlinx.serialization.Serializable
    private data class SessionResult(var status : String, var SessionID : String )
    @kotlinx.serialization.Serializable
    private data class StatusResult(var status : String )

    fun getAnalysis( symptoms : List<AnalyzeSymptom> ) = runBlocking( Dispatchers.IO ) {
        Log.e( "DiagnosisAPI" , "Making calls on: ${Thread.currentThread().name}")
        // TODO: Handle exceptions in networking here
        val sessionResponse = getSessionId()
        var result = HashMap<String,Float>()
        if(sessionResponse.status == "ok") {
            val sessionID = sessionResponse.SessionID
            val acceptTOUStatus = acceptTermsOfUse( sessionID )
            if( acceptTOUStatus.status == "ok" ) {
                for( symptom in symptoms ) {
                    uploadSymptom( symptom , sessionID )
                }
                result = HashMap( analyze( sessionID ) )
                Log.e( "DiagnosisAPI" , "Diagnosis Result: $result" )
            }
        }
        else {
            Log.d( "DiagnosisAPI" , "Session status not ok" )
        }
        return@runBlocking result
    }

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

    private fun analyze( sessionID: String ) : Map<String,Float> {
        val request = Request.Builder().run {
            url( HttpUrl.Builder().run {
                scheme( "https" )
                host( "api.endlessmedical.com" )
                addPathSegment( "v1" )
                addPathSegment( "dx" )
                addPathSegment( "Analyze" )
                addQueryParameter( "SessionID" , sessionID )
                build()
            } )
            get()
            build()
        }
        val response = client.newCall( request ).execute()
        val responseJson = response.body!!.string().trim()
        Log.d( "DiagnosisAPI" , "Analyze Response: " + responseJson )
        val responseObj =  JSONObject( responseJson )
        val output = HashMap<String,Float>()
        if( responseObj.get( "status" ) == "ok" ) {
            val diseases = responseObj.getJSONArray( "Diseases" )
            for( i in 0 until diseases.length() ) {
                diseases.getJSONObject( i ).apply {
                    for( key in this.keys() ) {
                        output[ key ] = (this.get( key ) as String ).toFloat()
                    }
                }
            }
        }
        return output
    }
    
}

