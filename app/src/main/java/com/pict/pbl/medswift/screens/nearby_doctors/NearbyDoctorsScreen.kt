package com.pict.pbl.medswift.screens.nearby_doctors

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import com.pict.pbl.medswift.location.CurrentLocation
import com.pict.pbl.medswift.screens.ui.theme.MedSwiftTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NearbyDoctorsScreen : ComponentActivity() {

    private val showLocationFetchProgress = mutableStateOf( false )

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

    private fun startLocationRetrieval() {
        showLocationFetchProgress.value = true
        CoroutineScope( Dispatchers.Main ).launch {
            CurrentLocation( this@NearbyDoctorsScreen ).getCurrentLocation {
                CoroutineScope( Dispatchers.Main ).launch {
                    showLocationFetchProgress.value = false
                    Log.e( "APP" , "Location received ${it.longitude} ${it.latitude}" )
                }
            }
        }
    }

    @Preview
    @Composable
    private fun ActivityUI() {
        ProgressDialog()
    }

    @Composable
    private fun ProgressDialog() {
        val errorMessageFlag by showLocationFetchProgress
        var openDialog by rememberSaveable{ mutableStateOf( true ) }
        if( errorMessageFlag && openDialog ){
            Dialog(
                onDismissRequest = { openDialog = false },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Column(
                    modifier = Modifier.background(Color.White, shape = RoundedCornerShape(8.dp) )
                ) {
                    Column( modifier = Modifier.padding( 32.dp ) , horizontalAlignment = Alignment.CenterHorizontally ) {
                        CircularProgressIndicator()
                        Text(text = "Fetching current location..." , modifier = Modifier.padding( 16.dp ) )
                    }
                }
            }
        }
    }

}



