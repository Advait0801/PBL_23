package com.pict.pbl.medswift.screens.history

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pict.pbl.medswift.data.Symptom
import com.pict.pbl.medswift.data.UserSymptom
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.viewmodels.HistoryViewModel
import com.pict.pbl.medswift.viewmodels.SymptomsViewModel
import kotlin.math.roundToInt

@Composable
fun ViewHistoryScreen( historyViewModel: HistoryViewModel ) {
    MedSwiftTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            ScreenUI( historyViewModel )
        }
    }
}

@Composable
private fun ScreenUI( historyViewModel: HistoryViewModel ) {
    // TODO: Improve ViewHistory lazycolumn here
    val diagnosis = historyViewModel.clickedDiagnosisItem
    LazyColumn {
        item {
            Text(
                text = "Symptoms" ,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth() ,
                style = TextStyle( fontWeight = FontWeight.Bold ) ,
                textAlign = TextAlign.Start ,
                fontSize = 12.sp
            )
        }
        items( diagnosis!!.symptoms ) {
            SymptomItem(symptom = it)
        }
        item {
            Text(
                text = "Predictions" ,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth() ,
                style = TextStyle( fontWeight = FontWeight.Bold ) ,
                textAlign = TextAlign.Start ,
                fontSize = 12.sp
            )
        }
        val min = diagnosis.predictions.minOf{ it.value }
        val max = diagnosis.predictions.maxOf{ it.value }
        items( diagnosis.predictions ) {
            DiseaseItem(name = it.name, confidence = ( ( it.value - min ) / ( max - min ) ) * 100)
        }
    }

}

@Composable
private fun SymptomItem( symptom : UserSymptom ) {
    // TODO: Add necessary styling to symptom text (here) in history
    Surface( modifier = Modifier
        .background(color = Color.White)
    ) {
        Row {
            Text(text = symptom.name ,
                fontSize = 14.sp ,
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            )
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