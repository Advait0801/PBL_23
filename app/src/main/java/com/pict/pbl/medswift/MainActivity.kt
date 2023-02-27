package com.pict.pbl.medswift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme

class MainActivity : ComponentActivity() {

    val blue1 = Color( 0xFF2055f5 )
    val blue2 = Color( 0xFF6185f2 )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedSwiftTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
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
            modifier = Modifier.drawBehind {
                drawCircle( color = blue1 , radius = 512.dp.toPx() )
                drawCircle( color = blue2 , radius = 320.dp.toPx() )
                drawCircle( color = Color.White , radius = 240.dp.toPx() )
            }
                ) {
            EmailId( modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp) )
            Password( modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp) )
            Button(
                onClick = {  } ,
                modifier = Modifier
                    .padding( 16.dp ) ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue1
                )) {
                Icon( imageVector = Icons.Default.ArrowForward , contentDescription = "Login" )
                Text(text = "Login")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun EmailId( modifier: Modifier ){
        var email by remember{ mutableStateOf("") }
        OutlinedTextField(
            modifier = modifier ,
            value = email,
            onValueChange = { it -> email = it } ,
            placeholder = { Text("Enter your EmailID" , color = Color.Black) } ,
            leadingIcon = { Icon( imageVector = Icons.Default.Email , contentDescription = "Email Address" ) }  ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                focusedLeadingIconColor = Color.Blue,
                containerColor = Color.White
            )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Password( modifier : Modifier ){
        var password by remember{ mutableStateOf("") }
        OutlinedTextField(
            modifier = modifier,
            value = password,
            onValueChange = { it -> password = it} ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                containerColor = Color.White
            ),
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password )
        )
    }

}

