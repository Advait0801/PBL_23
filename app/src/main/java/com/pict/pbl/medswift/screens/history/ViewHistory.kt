package com.pict.pbl.medswift.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pict.pbl.medswift.data.UserSymptom
import com.pict.pbl.medswift.ui.theme.BubbleText
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.ui.theme.ScreenTitleWithoutDivider
import com.pict.pbl.medswift.viewmodels.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

private val dateFormat = SimpleDateFormat( "E, dd MMM yyyy" , Locale.getDefault() )

@Composable
fun ViewHistoryScreen( historyViewModel: HistoryViewModel ) {
    MedSwiftTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer) ) {
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
            ScreenTitleWithoutDivider(title = dateFormat.format( historyViewModel.clickedDiagnosisItem!!.time ) )
        }
        item {
            BubbleText(
                text = "Symptoms" ,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
        items( diagnosis!!.symptoms.sortedBy{ it.value }.reversed() ) {
            SymptomItem(symptom = it)
            Divider(
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .width(1.dp)
            )
        }
        item {
            BubbleText(
                text = "Predictions" ,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
        val min = diagnosis.predictions.minOf{ it.value }
        val max = diagnosis.predictions.maxOf{ it.value }
        items( diagnosis.predictions.sortedBy{ it.value }.reversed() ) {
            DiseaseItem(name = it.name, confidence = ( ( it.value - min ) / ( max - min ) ) * 100)
            Divider(
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .width(1.dp)
            )
        }
    }

}

@Composable
private fun MyBox( content : ( () -> Unit ) ) {
    Box( modifier = Modifier
        .padding(8.dp)
        .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp)) )  {
        content()
    }
}

@Composable
private fun SymptomItem( symptom : UserSymptom ) {
    // TODO: Add necessary styling to symptom text (here) in history
    Row {
        Text(text = symptom.name ,
            fontSize = 16.sp ,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        )
    }
}

@Composable
private fun DiseaseItem( name : String , confidence : Float ) {
    Row {
        Text(text = name ,
            fontSize = 16.sp ,
            modifier = Modifier
                .background(Color.White)
                .weight(1f)
                .padding(16.dp)
        )
        Text(text = confidence.roundToInt().toString() ,
            color = Color.Red ,
            fontSize = 16.sp ,
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        )
    }
}