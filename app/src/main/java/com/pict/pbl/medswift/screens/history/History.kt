package com.pict.pbl.medswift.screens.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pict.pbl.medswift.api.UserDiagnosisHistory
import com.pict.pbl.medswift.data.UserDiagnosis
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.screens.ScreenTitle
import com.pict.pbl.medswift.viewmodels.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Locale

private val history = UserDiagnosisHistory().getHistory()
private val dateFormat = SimpleDateFormat( "E, dd MMM yyyy" , Locale.getDefault() )

@Composable
fun HistoryScreen( historyViewModel : HistoryViewModel ) {
    val navController = rememberNavController()
    NavHost( navController , startDestination = "main") {
        composable("main") { ScreenUI( historyViewModel ) }
        composable("viewHistory" ) { ViewHistoryScreen( historyViewModel ) }
    }
    historyViewModel.historyNavController = navController
}

@Composable
private fun ScreenUI( historyViewModel: HistoryViewModel ) {
    MedSwiftTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            LazyColumn {
                item {
                    ScreenTitle(title = "History")
                }
                items( history ) {
                    HistoryItem( diagnosis = it , historyViewModel )
                }
            }
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
