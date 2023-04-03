package com.pict.pbl.medswift.screens.symptoms

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pict.pbl.medswift.api.DiagnosisAPI
import com.pict.pbl.medswift.api.DiagnosisHistory
import com.pict.pbl.medswift.data.AnalyzeSymptom
import com.pict.pbl.medswift.data.Symptom
import com.pict.pbl.medswift.symptoms.SymptomsJSONReader
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.viewmodels.SymptomsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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
                symptomsViewModel.isLoading.value = true
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
            ProgressDialog()
        }
    }

    @Composable
    private fun ProgressDialog() {
        val progressShowFlag by symptomsViewModel.isLoading.observeAsState()
        var openDialog by rememberSaveable{ mutableStateOf( true ) }
        if (openDialog && progressShowFlag == true ) {
            Dialog(
                onDismissRequest = { openDialog = false },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment= Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}