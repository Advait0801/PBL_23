package com.pict.pbl.medswift.screens.prescriptions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.pict.pbl.medswift.api.UserPrescriptions
import com.pict.pbl.medswift.data.UserPrescription
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.screens.ScreenTitle
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

@Composable
private fun PrescriptionItem( prescription: UserPrescription ) {
    // TODO: Improve prescription UI item here
    Surface(
        shape = RoundedCornerShape( 16.dp ) ,
        border = BorderStroke( 1.dp , Color.Black ) ,
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.White),
        ) {
            Row( verticalAlignment = Alignment.CenterVertically ,
                modifier = Modifier.padding( vertical = 8.dp , horizontal = 16.dp ) ) {
                Text(
                    text = prescription.name ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth() ,
                    fontSize = 24.sp
                )
                Text(
                    text = "Completed" ,
                    textAlign = TextAlign.End ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth() ,
                    color = Color.DarkGray
                )
            }
            Row( modifier = Modifier.padding( vertical = 8.dp , horizontal = 16.dp ) ){
                Text(
                    text = "From:" ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth() ,
                    fontSize = 12.sp
                )
                Text(
                    text = dateFormat.format( prescription.startTime ) ,
                    textAlign = TextAlign.End ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth() ,
                    fontSize = 12.sp
                )
            }
            Row( modifier = Modifier.padding( vertical = 8.dp , horizontal = 16.dp ) ){
                Text(
                    text = "To:" ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth() ,
                    fontSize = 12.sp
                )
                calendar.time = prescription.startTime
                calendar.add( Calendar.DATE , prescription.courseLength )
                val adjustedDate = calendar.time
                Text(
                    text = dateFormat.format( adjustedDate ) ,
                    textAlign = TextAlign.End ,
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxWidth() ,
                    fontSize = 12.sp
                )
            }
            FlowRow( modifier = Modifier
                .background(Color.White)
                .padding(4.dp)
                .fillMaxWidth() ) {
                for( it in prescription.times ) {
                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(vertical = 8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically ,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(Color.Blue, RoundedCornerShape(16.dp))
                        ) {
                            Icon(imageVector = Icons.Default.LockClock, contentDescription = "Time", tint = Color.White , modifier = Modifier.padding(8.dp))
                            Text(text = it.toString() , color=Color.White, modifier = Modifier.padding(8.dp) )
                        }
                    }
                }
            }
        }
    }
}