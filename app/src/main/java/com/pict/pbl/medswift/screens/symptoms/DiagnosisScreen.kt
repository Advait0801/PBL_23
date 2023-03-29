package com.pict.pbl.medswift.screens.symptoms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
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
    val result = symptomsViewModel.diagnosisResult.toList()
    LazyColumn{
        items( result ) {
            DiseaseItem(name = it.first, confidence = it.second)
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
        Text(text = (confidence * 100).roundToInt().toString() ,
            fontSize = 14.sp ,
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        )
    }
}