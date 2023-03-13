package com.pict.pbl.medswift.screens

//import androidx.compose.material.icons.fille
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.pict.pbl.medswift.R
import com.pict.pbl.medswift.login.LoginManager
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme


class LoginScreen : ComponentActivity() {

    private val isLoginButtonEnabled = MutableLiveData( false )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            MedSwiftTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ActivityUI()
                }
            }

        }
        /*
        TODO: Listen for changes in internet connection, if the internet is off, show a warning to the user
         */
    }

    @Composable
    private fun ActivityUI(){
        val screenConfig = LocalConfiguration.current
        val screenWidth = screenConfig.screenWidthDp.dp
        println(screenWidth)
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterEnd
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxSize()
            ) {

            }
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .width(screenWidth)
                    .height(screenWidth)
                    .scale(1.7f)
                    .clip(CircleShape)
            ) { }
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .width(screenWidth)
                    .height(screenWidth)
                    .scale(1.4f)
                    .clip(CircleShape)
            ) { }
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val image = painterResource(id = R.drawable.app_icon )
                Image(
                    painter = image ,
                    contentDescription = "App Icon"
                )
                EmailId( modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Password( modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp) )
            }
            LoginButton(
                modifier = Modifier
                    .padding(top = 50.dp, end = 10.dp)
            )
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
            placeholder = { Text("Enter your EmailID") } ,
            leadingIcon = { Icon( imageVector = Icons.Default.Email , contentDescription = "Email Address" ) }  ,
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Email )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Password( modifier : Modifier ){
        var password by rememberSaveable{ mutableStateOf("") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(
            modifier = modifier,
            value = password,
            singleLine = true,
            onValueChange = { it -> password = it} ,
            placeholder = { Text("Password") } ,
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
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password )
        )
    }

    @Composable
    private fun LoginButton( modifier: Modifier ) {
        Button(
            onClick = {
               if( LoginManager.isUserLoggedIn() ) {
                   // TODO: Manage transition to HomeScreen, else show on-boarding screen
               }
               else {
                   // TODO: Manage user login error here
               }
            } ,
            modifier = modifier
                .defaultMinSize(minHeight = 0.2.dp , minWidth = 0.2.dp),
            contentPadding = PaddingValues(8.dp),
            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.background)
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Login"
            )
        }
    }

    @Preview
    @Composable
    private fun MedSwiftLogo(modifier: Modifier = Modifier) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = modifier
                .padding(24.dp)
        ){
           Text(
               "MedSwift",
               color = MaterialTheme.colorScheme.onTertiaryContainer,
               style = MaterialTheme.typography.headlineLarge.copy(
                   fontWeight = FontWeight.Bold
               )
//               fontStyle = MaterialTheme.typography.headlineLarge,
           )
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,

                modifier = Modifier
                    .size(40.dp)
                    .offset(x = 30.dp, y = -25.dp),


            )
        }
    }

}

