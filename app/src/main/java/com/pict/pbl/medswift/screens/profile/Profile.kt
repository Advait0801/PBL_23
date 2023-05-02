package com.pict.pbl.medswift.screens.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.modernstorage.photopicker.PhotoPicker
import com.pict.pbl.medswift.R
import com.pict.pbl.medswift.auth.CurrentUserDetails
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.ui.theme.ScreenTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat( "E, dd MMM yyyy" , Locale.getDefault() )
private val currentUserOptions = CurrentUserDetails()
private val currentUser = currentUserOptions.getUser()
private val userImage = MutableLiveData<Bitmap>()

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


@androidx.annotation.OptIn(androidx.core.os.BuildCompat.PrereleaseSdkCheck::class)
@Composable
private fun UserBasicInfo() {
    Surface(
        modifier = Modifier.fillMaxWidth() ,
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ScreenTitle(title = "Profile" , icon=Icons.Default.Person)
            UserImage( )
            Text(
                text = currentUser.firstName ,
                style = MaterialTheme.typography.titleLarge ,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )
            Text(
                text = currentUser.email ,
                style = MaterialTheme.typography.labelMedium ,
                color = Color.Gray ,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp, bottom = 16.dp)
            )
        }
    }
}
@androidx.annotation.OptIn(androidx.core.os.BuildCompat.PrereleaseSdkCheck::class)
@Composable
private fun ColumnScope.UserImage() {
    val context = LocalContext.current
    val userImageBitmap by userImage.observeAsState()
    val photoPicker = rememberLauncherForActivityResult( PhotoPicker() ) {
        userImage.value = BitmapFactory.decodeStream( context.contentResolver.openInputStream( it[0] ) )
        CoroutineScope( Dispatchers.IO ).launch {
            currentUserOptions.uploadImage( userImage.value!! )
        }
    }
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current).run {
            if( userImageBitmap != null ) {
                data( userImageBitmap )
            }
            else {
                try {
                    data( currentUserOptions.getUserImage() )
                }
                catch( e : Exception ) {
                    // TODO: Add no profile image case here
                    placeholder( R.drawable.ic_user_outline_24 )
                }
            }
            crossfade(true)
            build()
        } ,
        contentDescription = "Profile Photo" ,
        loading = {
            CircularProgressIndicator()
        } ,
        contentScale = ContentScale.Crop ,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .size(200.dp)
            .clip(RoundedCornerShape(100.dp))
            .clickable {
                photoPicker.launch(PhotoPicker.Args(PhotoPicker.Type.IMAGES_ONLY, 1))
            } ,
    )
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
            color = Color.Black ,
            style = MaterialTheme.typography.labelLarge
        )
    }

}