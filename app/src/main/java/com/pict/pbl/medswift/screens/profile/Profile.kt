package com.pict.pbl.medswift.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pict.pbl.medswift.R
import com.pict.pbl.medswift.auth.CurrentUser
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat( "E, dd MMM yyyy" , Locale.getDefault() )
private val currentUser = CurrentUser().getUser()

@Composable
fun ProfileScreen() {
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
    Column( modifier = Modifier.verticalScroll( rememberScrollState() ) ) {
        UserBasicInfo()
        OtherDetailsDrawer()
    }
}



@Composable
private fun UserBasicInfo() {
    Surface(
        modifier = Modifier.fillMaxWidth() ,
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Profile" ,
                color = Color.Black ,
                fontWeight = FontWeight.Bold ,
                fontSize = 22.sp ,
                modifier = Modifier
                    .padding( 24.dp )
                    .fillMaxWidth()
            )
            Divider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding( bottom = 8.dp )
                    .fillMaxWidth()
                    .width(1.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.sample_avatar),
                contentDescription = "Profile Photo" ,
                contentScale = ContentScale.Crop ,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(128.dp)
                    .clip(CircleShape)
                    .padding(top = 24.dp, bottom = 24.dp)
            )
            Text(
                text = currentUser.firstName ,
                fontWeight = FontWeight.Bold ,
                fontSize = 24.sp ,

                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )
            Text(
                text = currentUser.email ,
                fontSize = 16.sp ,
                color = Color.Gray ,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp, bottom = 16.dp)
            )
        }
    }
}


@Composable
private fun OtherDetailsDrawer() {
    Surface(
        modifier = Modifier.fillMaxWidth() ,
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier.padding(24.dp) ,
            verticalArrangement = Arrangement.spacedBy( 16.dp )
        ) {
            OtherDetail( Icons.Default.Phone , currentUser.phoneNumber )
            OtherDetail( Icons.Default.WaterDrop , currentUser.bldGrp )
            OtherDetail( Icons.Default.CalendarMonth , dateFormat.format( currentUser.dateOfBirth ) )
            OtherDetail( Icons.Default.Person , currentUser.gender )
            OtherDetail( Icons.Default.Person , currentUser.weight.toString() )
            OtherDetail( Icons.Default.Person , currentUser.height.toString() )
        }
    }
}

@Composable
private fun OtherDetail( icon : ImageVector , value : String ) {
    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(24.dp)
            )
            .fillMaxWidth()
            .padding(16.dp) ,
        horizontalArrangement = Arrangement.spacedBy( 8.dp )
    ) {
        Icon(imageVector = icon , contentDescription = value)
        Text(
            text = value ,
            color = Color.Black
        )
    }

}