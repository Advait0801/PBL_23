package com.pict.pbl.medswift.screens.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pict.pbl.medswift.api.UserDiagnosisHistory
import com.pict.pbl.medswift.data.UserDiagnosis
import com.pict.pbl.medswift.data.UserPrescription
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.screens.ScreenTitle
import com.pict.pbl.medswift.viewmodels.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Locale

private val historyState = mutableStateOf( ArrayList<UserDiagnosis>().toList() )
private val dateFormat = SimpleDateFormat( "E, dd MMM yyyy" , Locale.getDefault() )
private val errorFlag = mutableStateOf( false )
private val errorMessage = mutableStateOf( "" )

private val resultCallback : ( (List<UserDiagnosis>) -> Unit ) = { it ->
    historyState.value = it
}

private val errorCallback : ( (String) -> Unit ) = { it ->
    errorMessage.value = it
    errorFlag.value = true
}

@Composable
fun HistoryScreen( historyViewModel : HistoryViewModel ) {
    val navController = rememberNavController()
    NavHost( navController , startDestination = "main") {
        composable("main") { ScreenUI( historyViewModel ) }
        composable("viewHistory" ) { ViewHistoryScreen( historyViewModel ) }
    }
    historyViewModel.historyNavController = navController
    UserDiagnosisHistory().getHistory( resultCallback , errorCallback )
}

@Composable
private fun ScreenUI( historyViewModel: HistoryViewModel ) {
    MedSwiftTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            val history = remember{ historyState }
            AnimatedVisibility(visible = history.value.isNotEmpty()) {
                LazyColumn {
                    item {
                        ScreenTitle(title = "History")
                    }
                    items( history.value ) {
                        HistoryItem( diagnosis = it , historyViewModel )
                    }
                }
            }
            AnimatedVisibility(visible = history.value.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center ) {
                    CircularProgressIndicator()
                }
            }
            AlertDialog()
        }
    }
}

@Composable
private fun HistoryItem( diagnosis : UserDiagnosis , historyViewModel: HistoryViewModel ) {
    // TODO: Improve history text
    Surface(
        shape = RoundedCornerShape( 10.dp ) ,
        border = BorderStroke( 1.dp , MaterialTheme.colorScheme.primary ) ,
        modifier = Modifier.padding( horizontal = 8.dp , vertical = 4.dp )
    ) {
        Text(
            text = dateFormat.format( diagnosis.time ) ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    historyViewModel.clickedDiagnosisItem = diagnosis
                    historyViewModel.historyNavController?.navigate("viewHistory")
                }
        )
    }

}

@Composable
private fun AlertDialog() {
    val errorMessageFlag = remember{ errorFlag }
    val errorMessage = remember{ errorMessage }
    var openDialog by rememberSaveable{ mutableStateOf( true ) }
    if(errorMessageFlag.value && openDialog){
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { openDialog = false },
            title = { Text("Error Message") },
            text = { Text(errorMessage.value) },
            confirmButton = {
                Button(onClick = {
                    openDialog = false
                    errorMessageFlag.value = false
                }) {
                    Text(text = "CANCEL")
                }
            },
        )
    }
}

