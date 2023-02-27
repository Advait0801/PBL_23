package com.pict.pbl.medswift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedSwiftTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ActivityUI()

                }
            }
        }
    }

    @Preview
    @Composable
    private fun ActivityUI(){
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
                ) {
            EmailId(modifier = Modifier.fillMaxWidth())
            Password(modifier = Modifier.fillMaxWidth())

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun EmailId( modifier: Modifier ){
        var email by remember{ mutableStateOf("") }
        TextField(
            modifier = modifier ,
            value = email,
            onValueChange = { it -> email = it } ,
            placeholder = { Text("Enter your EmailID")}
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Password( modifier : Modifier ){
        var password by remember{ mutableStateOf("") }
        TextField(
            modifier = modifier,
            value = password,
            onValueChange = { it -> password = it}
        )
    }

}

