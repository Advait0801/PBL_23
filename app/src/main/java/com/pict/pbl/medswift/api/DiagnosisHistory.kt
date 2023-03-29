package com.pict.pbl.medswift.api

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.pict.pbl.medswift.data.UserDiagnosis
import com.pict.pbl.medswift.data.UserPrediction
import com.pict.pbl.medswift.data.UserSymptom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class DiagnosisHistory {

    private val auth = Firebase.auth

    fun getHistory() = runBlocking( Dispatchers.IO ){
        if( auth.currentUser?.uid != null ) {
            val database = Firebase.firestore
            val diagnosis = database
                .collection( "users" )
                .document( auth.currentUser!!.uid )
                .collection( "diagnoses" )
            val prediction = diagnosis.get().await()
            println( "Doc: "  + prediction.documents.size )
            val allUserDiagnosis = ArrayList<UserDiagnosis>()
            diagnosis
                .get()
                .await()
                .documents.forEach {
                    Log.e( "APP" , "Doc: ${it.id}" )
                    val userPredictions = ArrayList<UserPrediction>()
                    val userSymptoms = ArrayList<UserSymptom>()
                    val predictions = it.reference.collection( "predictions" ).get().await()
                    for( pred in predictions.documents ) {
                        userPredictions.add( pred.toObject( UserPrediction::class.java )!! )
                    }
                    val symptoms = it.reference.collection( "symptoms" ).get().await()
                    for( symptom in symptoms.documents ) {
                        userSymptoms.add( symptom.toObject( UserSymptom::class.java )!! )
                    }
                    allUserDiagnosis.add( UserDiagnosis( userPredictions , userSymptoms ) )
                }
            return@runBlocking allUserDiagnosis.toList()
        }
        else {
            return@runBlocking emptyList()
        }

    }

}