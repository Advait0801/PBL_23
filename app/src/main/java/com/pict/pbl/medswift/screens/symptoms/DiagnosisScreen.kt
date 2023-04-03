package com.pict.pbl.medswift.screens.symptoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.viewmodels.SymptomsViewModel
import kotlin.math.roundToInt

@Composable
fun DiagnosisScreen( symptomsViewModel: SymptomsViewModel ) {
    MedSwiftTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            ScreenUI( symptomsViewModel )
        }
    }
}

@Composable
private fun ScreenUI( symptomsViewModel: SymptomsViewModel ) {
    // TODO: Improve the UI of DiagnosisScreen
    symptomsViewModel.isLoading.value = false
    val result = symptomsViewModel.diagnosisResult.toList()
    val min = result.minOf{ it.second }
    val max = result.maxOf{ it.second }
    LazyColumn{
        items( result ) {
            DiseaseItem(name = it.first, confidence = ( ( it.second - min ) / ( max - min ) ) * 100 )
        }
    }
}

@Composable
private fun DiseaseItem( name : String , confidence : Float ) {
    Row {
        Text(text = name ,
            fontSize = 14.sp ,
            modifier = Modifier
                .background(Color.White)
                .weight(1.0f)
                .padding(16.dp)
        )
        Text(text = confidence.roundToInt().toString() ,
            fontSize = 14.sp ,
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        )
    }
}