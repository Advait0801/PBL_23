package com.pict.pbl.medswift.screens.prescriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.pict.pbl.medswift.api.UserPrescriptions
import com.pict.pbl.medswift.data.UserPrescription
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.ui.theme.ScreenTitle
import java.text.SimpleDateFormat
import java.util.*

private val prescriptions = UserPrescriptions().getPrescriptions()
private val dateFormat = SimpleDateFormat( "dd/MM/yyyy" , Locale.getDefault() )
private val calendar = Calendar.getInstance()

@Composable
fun PrescriptionsScreen() {
    MedSwiftTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            ScreenUI()
        }
    }
}

@Composable
private fun ScreenUI() {
    LazyColumn {
        item {
            ScreenTitle(title = "Prescriptions")
        }
        items( prescriptions ) {
            PrescriptionItem( it )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrescriptionItem( prescription: UserPrescription ) {
    // TODO: Improve prescription UI item here
    Surface(modifier = Modifier
        .padding(vertical = 16.dp, horizontal = 8.dp)
        .background(Color.White) ) {
        Column {
            Row {
                Text(
                    text = prescription.name ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                )
                Text(
                    text = "Completed" ,
                    textAlign = TextAlign.End ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                )
            }
            Row {
                Text(
                    text = "From:" ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                )
                Text(
                    text = dateFormat.format( prescription.startTime ) ,
                    textAlign = TextAlign.End ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                )
            }
            Row {
                Text(
                    text = "To:" ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                )
                calendar.time = prescription.startTime
                calendar.add( Calendar.DATE , prescription.courseLength )
                val adjustedDate = calendar.time
                Text(
                    text = dateFormat.format( adjustedDate ) ,
                    textAlign = TextAlign.End ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth()
                )
            }
            FlowRow( modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth() ) {
                for( it in prescription.times ) {
                    Row(
                        modifier = Modifier.background( Color.White ).padding( vertical = 8.dp )) {
                        Icon(imageVector = Icons.Default.LockClock, contentDescription = "Time")
                        Text(text = it.toString())
                    }
                }
            }
        }
    }

}