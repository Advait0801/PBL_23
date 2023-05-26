package com.pict.pbl.medswift.screens.nearby_doctors

import android.preference.PreferenceManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.pict.pbl.medswift.data.Doctor
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.viewmodels.NearbyDoctorsViewModel
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

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
            // Text(text = doctor.name)
            // Text(text = doctor.degree)
            Map( doctor )
        }
    }
}

@Composable
fun Map( doctor : Doctor ) {
    AndroidView(
        modifier = Modifier.fillMaxSize() ,
        factory = { it ->
            MapView( it ).apply {

                Configuration.getInstance().load( it , PreferenceManager.getDefaultSharedPreferences(it))
                setTileSource( TileSourceFactory.MAPNIK )
                setMultiTouchControls( true )

                val doctorLocation = GeoPoint( doctor.latitude.toDouble() + 0.001 , doctor.longitude.toDouble() + 0.003 )

                val mapController : IMapController = this.controller
                mapController.setZoom( 18.0 )
                mapController.animateTo( doctorLocation )

                val marker = Marker( this )
                marker.position = doctorLocation
                marker.title = "${doctor.name}\nSpeciality: ${doctor.speciality}\nRating: ${doctor.rating}"
                marker.textLabelBackgroundColor = android.graphics.Color.BLUE;
                marker.textLabelForegroundColor = android.graphics.Color.WHITE;
                marker.textLabelFontSize = 40;
                marker.setTextIcon( doctor.name )
                marker.setAnchor( Marker.ANCHOR_CENTER , Marker.ANCHOR_CENTER )
                this.overlays.add( marker )
            }
        }
    )
}
