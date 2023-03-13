package com.pict.pbl.medswift.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pict.pbl.medswift.R

class ProfileScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainUI()
        }
    }

    @Preview
    @Composable
    private fun MainUI() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            ActivityUI()
        }
    }

    @Composable
    private fun ActivityUI() {
        Column {
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
                        .padding(start = 24.dp, top = 24.dp, end = 24.dp)
                        .fillMaxWidth()
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
                    text = "Shubham Panchal" ,
                    fontWeight = FontWeight.Bold ,
                    fontSize = 24.sp ,

                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )
                Text(
                    text = "equipintelligence@gmail.com" ,
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
                OtherDetail()
                OtherDetail()
            }
        }
    }

    @Preview
    @Composable
    private fun OtherDetail() {
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
            Icon(imageVector = Icons.Default.Call, contentDescription = "Phone number")
            Text(
                text = "Hello World" ,
                color = Color.Black
            )
        }

    }

}