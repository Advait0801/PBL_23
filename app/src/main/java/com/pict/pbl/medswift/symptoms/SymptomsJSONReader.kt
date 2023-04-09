package com.pict.pbl.medswift.symptoms

import android.content.Context
import android.util.Log
import com.pict.pbl.medswift.R
import com.pict.pbl.medswift.data.Symptom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import org.json.JSONArray
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SymptomsJSONReader( private val context : Context ){

    private val symptomsJsonFileName = "symptoms_output.json"
    private val sharedPreferences = context.getSharedPreferences( context.getString( R.string.app_name ) , Context.MODE_PRIVATE )

    @OptIn(ExperimentalSerializationApi::class)
    fun getSymptoms() : ArrayList<Symptom> = runBlocking( Dispatchers.IO ){
        if ( sharedPreferences.getBoolean( context.getString( R.string.key_shared_pref_symptoms_store ) , false ) ) {
            println("Read symptoms")
            val fileInputStream = FileInputStream(
                File(
                    context.filesDir,
                    context.getString(R.string.filename_symptoms_blob)
                )
            )
            return@runBlocking Json.decodeFromStream<ArrayList<Symptom>>(fileInputStream)
        }
        else {
            println( "Parsed symptoms" )
            val symptomsList = parseSymptoms()
            val fileOutputStream = FileOutputStream( File( context.filesDir , context.getString( R.string.filename_symptoms_blob ) ) )
            Json.encodeToStream( symptomsList , fileOutputStream )
            fileOutputStream.flush()
            fileOutputStream.close()
            sharedPreferences.edit().putBoolean( context.getString( R.string.key_shared_pref_symptoms_store ) , true ).apply()
            return@runBlocking symptomsList
        }
    }

    private fun parseSymptoms() : ArrayList<Symptom> = runBlocking( Dispatchers.IO ){
        val jsonString = readJsonAsString()
        Log.d( "APP" ,"String: " + jsonString )
        val symptomsArray = JSONArray( jsonString )
        val symptomsList = ArrayList<Symptom>()
        for( i in 0 until symptomsArray.length() ) {
            val symptom = Json.decodeFromString<Symptom>( symptomsArray[i].toString() )
            symptomsList.add( symptom )
        }
        return@runBlocking symptomsList
    }

    private fun readJsonAsString() : String {
        context.assets.open( symptomsJsonFileName ).apply {
            val size: Int = this.available()
            val buffer = ByteArray(size)
            read(buffer)
            close()
            return String(buffer, Charsets.UTF_8 )
        }
    }


}
