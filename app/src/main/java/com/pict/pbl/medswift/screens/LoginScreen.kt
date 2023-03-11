package com.pict.pbl.medswift.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.pict.pbl.medswift.R
import com.pict.pbl.medswift.login.LoginManager


class LoginScreen : ComponentActivity() {

    val blue1 = Color( 0xFF2055f5 )
    val blue2 = Color( 0xFF6185f2 )
    private val isLoginButtonEnabled = MutableLiveData( false )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                ActivityUI()
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun ActivityUI(){
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterEnd
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.drawBehind {
                    drawCircle( color = blue1 , radius = 512.dp.toPx() )
                    drawCircle( color = blue2 , radius = 320.dp.toPx() )
                    drawCircle( color = Color.White , radius = 240.dp.toPx() )
                }) {
                val image = painterResource(id = R.drawable.app_icon )
                Image(
                    painter = image ,
                    contentDescription = "App Icon"
                )
                EmailId( modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp) )
                Spacer(modifier = Modifier.height(16.dp))
                Password( modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp) )
            }
            LoginButton(modifier = Modifier.padding(top = 50.dp))
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun EmailId( modifier: Modifier ){
        var email by remember{ mutableStateOf("") }
        OutlinedTextField(
            modifier = modifier ,
            value = email,
            singleLine = true,
            onValueChange = {
                email = it
                isLoginButtonEnabled.value = LoginManager.checkEmailAddress( it )
            } ,
            placeholder = { Text("Enter your EmailID" , color = Color.Black) } ,
            leadingIcon = { Icon( imageVector = Icons.Default.Email , contentDescription = "Email Address" ) }  ,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                focusedLeadingIconColor = Color.Blue,
                containerColor = Color.White
            ) ,
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Email )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Password( modifier : Modifier ){
        var password by remember{ mutableStateOf("") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(
            modifier = modifier,
            value = password,
            singleLine = true,
            onValueChange = { it -> password = it} ,
            placeholder = { Text("Password" , color = Color.Black) } ,
            leadingIcon = { Icon( imageVector = Icons.Default.Lock , contentDescription = "Email Address" ) } ,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                containerColor = Color.White
            ),
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password )
        )
    }

    @Composable
    private fun LoginButton( modifier: Modifier ) {
        val isEnabled by isLoginButtonEnabled.observeAsState()
        Button(
            onClick = {  } ,
            modifier = modifier,
            enabled = true ,
            colors = ButtonDefaults.buttonColors(
                containerColor = blue1
            )) {
            Icon( imageVector = Icons.Default.ArrowForward , contentDescription = "Login" )
        }
    }

}

