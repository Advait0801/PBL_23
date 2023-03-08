package com.pict.pbl.medswift.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

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
        UserBasicInfo()
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
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    private fun OtherDetailsDrawer() {

    }

}