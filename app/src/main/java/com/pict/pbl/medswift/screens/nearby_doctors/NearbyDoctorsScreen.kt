package com.pict.pbl.medswift.screens.nearby_doctors

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import com.pict.pbl.medswift.api.NearbyDoctors
import com.pict.pbl.medswift.data.Doctor
import com.pict.pbl.medswift.location.CurrentLocation
import com.pict.pbl.medswift.screens.RatingBar
import com.pict.pbl.medswift.screens.ScreenTitle
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NearbyDoctorsScreen : ComponentActivity() {

    private val showProgress = mutableStateOf( false )
    private val progressDialogText = mutableStateOf( "" )
    private val nearbyDoctorsList = mutableStateOf( ArrayList<Doctor>() )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MedSwiftTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ActivityUI()
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
        else {
            startLocationRetrieval()
        }

    }

    private val requestLocationPermissionLauncher = registerForActivityResult( ActivityResultContracts.RequestMultiplePermissions() ) {
        if( (it[ Manifest.permission.ACCESS_FINE_LOCATION ] != false) && (it[ Manifest.permission.ACCESS_COARSE_LOCATION ] != false)) {
            // Both permissions were granted
            startLocationRetrieval()
        }
    }

    private fun getNearbyDoctorsList( lat : Float , lng : Float ) {
        progressDialogText.value = "Searching nearby doctors..."
        CoroutineScope( Dispatchers.Main ).launch {
            val doctors = NearbyDoctors().getNearbyDoctors( lat , lng ).await()
            Log.e( "APP" , "Doctors are ${doctors.size}" )
            showProgress.value = false
            nearbyDoctorsList.value = doctors
        }
    }

    private fun startLocationRetrieval() {
        progressDialogText.value = "Fetching location..."
        showProgress.value = true
        CoroutineScope( Dispatchers.Main ).launch {
            CurrentLocation( this@NearbyDoctorsScreen ).getCurrentLocation {
                getNearbyDoctorsList( it.latitude.toFloat() , it.longitude.toFloat() )
            }
        }
    }

    @Preview
    @Composable
    private fun ActivityUI() {
        Column {
            ScreenTitle(title = "Nearby Doctors")
            DoctorsList()
            ProgressDialog()
        }
    }

    @Composable
    private fun DoctorsList() {
        val doctorsList by nearbyDoctorsList
        LazyColumn {
            items( doctorsList ) {
                DoctorItem(doctor = it)
            }
        }
    }

    @Composable
    private fun DoctorItem( doctor : Doctor ) {
        Surface(
            shape = RoundedCornerShape( 10.dp ) ,
            border = BorderStroke( 1.dp , MaterialTheme.colorScheme.secondary ) ,
            modifier = Modifier
                .padding( 8.dp )
                .clickable {

                }
        ) {
            Column( modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth() ) {
                Text(text = doctor.name, style=MaterialTheme.typography.bodyLarge)
                Text(text = doctor.degree, style=MaterialTheme.typography.bodySmall)
                RatingBar(rating = doctor.rating.toDouble())
            }
        }

    }

    @Composable
    private fun ProgressDialog() {
        val progressFlag by showProgress
        val progressMessage by progressDialogText
        var openDialog by rememberSaveable{ mutableStateOf( true ) }
        if( progressFlag && openDialog ){
            Dialog(
                onDismissRequest = { openDialog = false },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp) )
                ) {
                    Column( modifier = Modifier.padding( 16.dp ) , horizontalAlignment = Alignment.CenterHorizontally ) {
                        CircularProgressIndicator()
                        Text(text = progressMessage , style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp) , textAlign = TextAlign.Center )
                    }
                }
            }
        }
    }

}



