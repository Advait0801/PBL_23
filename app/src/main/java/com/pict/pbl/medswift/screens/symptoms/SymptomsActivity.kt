package com.pict.pbl.medswift.screens.symptoms

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.flowlayout.FlowRow
import com.pict.pbl.medswift.R
import com.pict.pbl.medswift.api.DiagnosisAPI
import com.pict.pbl.medswift.api.UserDiagnosisHistory
import com.pict.pbl.medswift.auth.CurrentUserDetails
import com.pict.pbl.medswift.data.AnalyzeSymptom
import com.pict.pbl.medswift.data.Symptom
import com.pict.pbl.medswift.data.UserPrediction
import com.pict.pbl.medswift.data.UserSymptom
import com.pict.pbl.medswift.symptoms.SymptomsJSONReader
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.screens.ScreenTitleWithoutDivider
import com.pict.pbl.medswift.viewmodels.SymptomsViewModel
import okhttp3.OkHttpClient

class SymptomsActivity : ComponentActivity() {
    
    private lateinit var symptoms : ArrayList<Symptom>
    private val symptomsViewModel : SymptomsViewModel by viewModels()
    private var mappedSymptoms = HashMap<String,AnalyzeSymptom>()
    private val userDiagnosisHistory = UserDiagnosisHistory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SymptomsJSONReader(this).apply {
            symptomsViewModel.symptomsList.value = getSymptoms()
        }

        val history = UserDiagnosisHistory().getHistory( )
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

    @Preview
    @Composable
    private fun ActivityUI() {
        // Improve UI of SymptomsActivity
        Column( Modifier.verticalScroll( rememberScrollState() ) ) {
            ScreenTitleWithoutDivider(title = "Diagnosis")
            Box( contentAlignment = Alignment.Center ){
                Image(painter = painterResource(id = R.drawable.symptoms_screen_pic), contentDescription = "Symptoms Screen" )
            }
            SelectedSymptoms()
            Row( verticalAlignment = Alignment.Bottom ){
                Button(
                    onClick = {
                        println( "SelectSymptoms - select symptoms" )
                        println( symptomsViewModel.navController )

                        symptomsViewModel.navController?.navigate( "selectSymptoms" )
                    } ,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Select Symptoms")
                }
                Button(onClick = { initiateDiagnosis() } ,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Analyze")
                }
            }
            AlertDialog()
            ProgressDialog()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SelectedSymptoms() {
        val analyzeSymptomList by symptomsViewModel.analyzeSymptomsList.observeAsState()
        FlowRow(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth() ,
            mainAxisSpacing = 2.dp,
            crossAxisSpacing = 2.dp,
        ) {
            ( analyzeSymptomList ?: ArrayList() ).forEach {
                AssistChip(
                    onClick = {  } ,
                    label = { Text(text = it.name , maxLines = 1 ) } ,
                )
            }
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

    @Composable
    private fun AlertDialog() {
        val errorMessageFlag by symptomsViewModel.errorMessageFlag.observeAsState()
        var openDialog by rememberSaveable{ mutableStateOf( true ) }
        if( errorMessageFlag == true && openDialog ){
            AlertDialog(
                onDismissRequest = { openDialog = false } ,
                title = { Text( "Error Message" ) } ,
                text = { Text( symptomsViewModel.errorMessage.value ?: "" ) } ,
                confirmButton = {
                    Button(onClick = { openDialog = false } ){ Text(text = "CANCEL")} } ,
            )
        }
    }

    private fun initiateDiagnosis() {
        try {
            val client = OkHttpClient()
            //symptomsViewModel.isLoading.value = true
            DiagnosisAPI(client).apply {
                val analyzeSymptomsList = symptomsViewModel.analyzeSymptomsList.value ?: ArrayList()
                mappedSymptoms = HashMap()
                analyzeSymptomsList.forEach{
                    mappedSymptoms[ it.name ] = it
                }
                val result = getAnalysis( mappedSymptoms.values.toList() )
                if( result.size > 0 ) {
                    userDiagnosisHistory.insertDiagnosis(
                        result.map{ UserPrediction( it.key , it.value ) }.toTypedArray() ,
                        analyzeSymptomsList.map { UserSymptom( it.name , it.value.toInt() ) }.toTypedArray()
                    )
                    symptomsViewModel.diagnosisResult = result
                    symptomsViewModel.navController!!.navigate( "diagnosis" )
                }
            }
        }
        catch( e : Exception ) {
            symptomsViewModel.errorMessageFlag.value = true
            symptomsViewModel.errorMessage.value = e.message
        }

    }

}