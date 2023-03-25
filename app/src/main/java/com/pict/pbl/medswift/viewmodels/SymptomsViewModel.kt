package com.pict.pbl.medswift.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.pict.pbl.medswift.data.AnalyzeSymptom
import com.pict.pbl.medswift.data.Symptom

class SymptomsViewModel: ViewModel() {

    val symptomsList = MutableLiveData<ArrayList<Symptom>>()
    val selectedSymptomsList = MutableLiveData<ArrayList<Symptom>>()
    val analyzeSymptomsList = MutableLiveData<ArrayList<AnalyzeSymptom>>()

    var navController : NavController? = null
    var clickedSymptomIndex : Int = 0

    fun addNewSelectedSymptom( symptom: Symptom ) {
        val selectedSymptoms = ArrayList<Symptom>()
        selectedSymptoms.addAll(selectedSymptomsList.value ?: ArrayList() )
        selectedSymptoms.add( symptom )
        selectedSymptomsList.value = selectedSymptoms
    }

    fun addNewAnalyzeSymptom( analyzeSymptom: AnalyzeSymptom ) {
        val analyzeSymptoms = ArrayList<AnalyzeSymptom>()
        analyzeSymptoms.addAll(analyzeSymptomsList.value ?: ArrayList() )
        analyzeSymptoms.map{
            if( it.name == analyzeSymptom.name ) {
                it.value = analyzeSymptom.value
            }
        }
        analyzeSymptomsList.value = analyzeSymptoms
    }

}