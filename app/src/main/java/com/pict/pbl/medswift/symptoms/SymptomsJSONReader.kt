package com.pict.pbl.medswift.symptoms

import android.content.Context
import android.util.Log
import com.pict.pbl.medswift.data.Symptom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray

class SymptomsJSONReader( private val context : Context ){

    private val symptomsJsonFileName = "symptoms_output.json"

    fun parseSymptoms() : ArrayList<Symptom> = runBlocking( Dispatchers.IO ){
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
