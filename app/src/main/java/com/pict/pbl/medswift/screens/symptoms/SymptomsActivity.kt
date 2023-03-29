package com.pict.pbl.medswift.screens.symptoms

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pict.pbl.medswift.api.DiagnosisAPI
import com.pict.pbl.medswift.api.DiagnosisHistory
import com.pict.pbl.medswift.data.AnalyzeSymptom
import com.pict.pbl.medswift.data.Symptom
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.symptoms.SymptomsJSONReader
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

        val history = DiagnosisHistory().getHistory( )
        Log.e( "APP" , "History $history")

        setContent {
            MedSwiftTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "main") {
                        composable("main") { ActivityUI() }
                        composable("selectSymptoms" ) { SelectSymptomsScreen( symptomsViewModel ) }
                        composable( "inputSymptoms" ){ InputSymptomsScreen( symptomsViewModel ) }
                        composable( "diagnosis" ){ DiagnosisScreen( symptomsViewModel ) }
                    }
                    symptomsViewModel.navController = navController
                }
            }
        }
    }


    @Composable
    private fun ActivityUI() {
        // Improve UI of SymptomsActivity
        Column {
            Button(onClick = {
                println( "SelectSymptoms - select symptoms" )
                println( symptomsViewModel.navController )
                symptomsViewModel.navController?.navigate( "selectSymptoms" )
            }) {
                Text(text = "Select Symptoms")
            }
            Button(onClick = {
                val client = OkHttpClient()
                DiagnosisAPI(client).apply {
                    val analyzeSymptomsList = symptomsViewModel.analyzeSymptomsList.value ?: ArrayList()
                    val mappedSymptoms = HashMap<String,AnalyzeSymptom>()
                    analyzeSymptomsList.forEach{
                        mappedSymptoms[ it.name ] = it
                    }
                    val result = getAnalysis( mappedSymptoms.values.toList() )
                    symptomsViewModel.diagnosisResult = result
                    symptomsViewModel.navController!!.navigate( "diagnosis" )
                }
            }) {
                Text(text = "Analyze")
            }
        }
    }

}