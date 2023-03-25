package com.pict.pbl.medswift.screens

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pict.pbl.medswift.api.DiagnosisAPI
import com.pict.pbl.medswift.data.Symptom
import com.pict.pbl.medswift.symptoms.SymptomsJSONReader
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.viewmodels.SymptomsViewModel
import okhttp3.OkHttpClient

class SymptomsActivity : ComponentActivity() {
    
    private lateinit var symptoms : ArrayList<Symptom>
    private val symptomsViewModel : SymptomsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SymptomsJSONReader(this).apply {
            symptomsViewModel.symptomsList.value = parseSymptoms()
        }



        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "main") {
                composable("main") { ActivityUI() }
                composable("selectSymptoms") { SelectedSymptomsScreen( symptomsViewModel ) }
                composable( "inputSymptoms" ){ InputSymptomsScreen( symptomsViewModel ) }
            }
            symptomsViewModel.navController = navController
            /*
            MedSwiftTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    //ActivityUI()
                }
            }

             */
        }
    }

    @Composable
    private fun ActivityUI() {
        Column {
            Button(onClick = {
                symptomsViewModel.navController?.navigate( "selectSymptoms" )
            }) {
                Text(text = "Select Symptoms")
            }
            Button(onClick = {
                val client = OkHttpClient()
                DiagnosisAPI(client).apply {
                    symptomsViewModel.analyzeSymptomsList.value!!.forEach{
                        Log.e( "APP" , "AnalyzeSymptom -> $it")
                    }
                    getAnalysis( symptomsViewModel.analyzeSymptomsList.value!! )
                }
            }) {
                Text(text = "Analyze")
            }
        }
    }

}