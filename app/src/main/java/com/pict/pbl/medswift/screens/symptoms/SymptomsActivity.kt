package com.pict.pbl.medswift.screens.symptoms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.pict.pbl.medswift.api.DiagnosisAPI
import com.pict.pbl.medswift.data.AnalyzeSymptom
import com.pict.pbl.medswift.data.Symptom
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.symptoms.SymptomsJSONReader
import com.pict.pbl.medswift.viewmodels.SymptomsViewModel
import okhttp3.OkHttpClient

class SymptomsActivity : ComponentActivity() {
    
    private lateinit var symptoms : ArrayList<Symptom>
    private val symptomsViewModel : SymptomsViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SymptomsJSONReader(this).apply {
            symptomsViewModel.symptomsList.value = parseSymptoms()
        }

        setContent {
            val navController = rememberAnimatedNavController()
            AnimatedNavHost(navController, startDestination = "main") {
                // TODO: Add composable transitions here
                composable("main") { ActivityUI() }
                composable("selectSymptoms" ,
                    enterTransition = {
                        fadeIn()
                    } ) { SelectedSymptomsScreen( symptomsViewModel ) }
                composable( "inputSymptoms" ){ InputSymptomsScreen( symptomsViewModel ) }
                composable( "diagnosis" ){ DiagnosisScreen( symptomsViewModel ) }
            }
            symptomsViewModel.navController = navController
            MedSwiftTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ActivityUI()
                }
            }
        }
    }


    @Composable
    private fun ActivityUI() {
        // Improve UI of SymptomsActivity
        Column {
            Button(onClick = {
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