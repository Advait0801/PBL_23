package com.pict.pbl.medswift.screens.nearby_doctors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.viewmodels.NearbyDoctorsViewModel

@Composable
fun ViewDoctorScreen( nearbyDoctorsViewModel: NearbyDoctorsViewModel ) {
    MedSwiftTheme {
        Surface( modifier = Modifier
            .background(Color.White)
            .fillMaxWidth() ) {
            ScreenUI( nearbyDoctorsViewModel )
        }
    }
}

@Composable
fun ScreenUI( nearbyDoctorsViewModel: NearbyDoctorsViewModel ) {
    val doctor = nearbyDoctorsViewModel.clickedNearbyDoctor.value
    if( doctor != null ) {
        Column {
            Text(text = doctor.name)
            Text(text = doctor.degree)
        }
    }
}