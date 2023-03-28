package com.pict.pbl.medswift.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.pict.pbl.medswift.data.AnalyzeSymptom
import com.pict.pbl.medswift.data.Symptom

class SymptomsViewModel: ViewModel() {

    val symptomsList = MutableLiveData<ArrayList<Symptom>>()
    val selectedSymptomsList = MutableLiveData<ArrayList<Symptom>>()

    val analyzeSymptomsList = MutableLiveData<ArrayList<AnalyzeSymptom>>()
    var diagnosisResult : Map<String,Float> = HashMap()

    var navController : NavController? = null
    var clickedSymptomIndex : Int = 0
    var clickedSymptom : Symptom? = null

    fun addNewSelectedSymptom( symptom: Symptom ) {
        val selectedSymptoms = ArrayList<Symptom>()
        selectedSymptoms.addAll(selectedSymptomsList.value ?: ArrayList() )
        selectedSymptoms.add( symptom )
        selectedSymptomsList.value = selectedSymptoms
    }

    fun removeSelectedSymptom( symptom : Symptom ) {
        val selectedSymptoms = ArrayList<Symptom>()
        selectedSymptoms.addAll(selectedSymptomsList.value ?: ArrayList() )
        selectedSymptoms.remove( symptom )
        selectedSymptoms.forEach{
            Log.e( "APP" , "Selected Symptom -> ${it}")
        }
        selectedSymptomsList.value = selectedSymptoms
    }


    fun addNewAnalyzeSymptom( analyzeSymptom: AnalyzeSymptom ) {
        val analyzeSymptoms = ArrayList<AnalyzeSymptom>()
        analyzeSymptoms.addAll(analyzeSymptomsList.value ?: ArrayList() )
        analyzeSymptoms.add( analyzeSymptom )
        analyzeSymptomsList.value = analyzeSymptoms
    }

}