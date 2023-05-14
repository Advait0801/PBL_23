package com.pict.pbl.medswift.screens.user_auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pict.pbl.medswift.auth.CurrentUserDetails
import com.pict.pbl.medswift.data.User
import com.pict.pbl.medswift.screens.DatePicker
import com.pict.pbl.medswift.screens.DropDownInput
import com.pict.pbl.medswift.screens.ScreenTitle
import com.pict.pbl.medswift.screens.TextInput
import com.pict.pbl.medswift.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterScreen : ComponentActivity() {

    private val currentUser = User()
    private val auth = Firebase.auth
    private var userPassword = ""
    private val genderOptions = listOf( "Male" , "Female" )
    private val bloodGroupOptions = listOf(
        "B +ve" ,
        "B -ve" ,
        "O +ve" ,
        "O -ve" ,
        "AB +ve" ,
        "AB -ve"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedSwiftTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    ActivityUI()
                }
            }
        }
    }

    private val modifier = Modifier
        .padding(vertical = 16.dp, horizontal = 16.dp)
        .fillMaxWidth()

    @Preview
    @Composable
    private fun ActivityUI() {
        val context = LocalContext.current
        Column( modifier = Modifier.verticalScroll( rememberScrollState() ) ){
            ScreenTitle(title = "Register", icon = Icons.Default.PersonAdd)
            TextInput(
                label = "Email Address",
                onValueChange = { currentUser.email = it } ,
                keyboardType = KeyboardType.Email ,
                icon = Icons.Default.Email ,
                modifier = modifier
            )
            UserPassword(modifier = modifier)
            TextInput(
                label = "First Name",
                onValueChange = { currentUser.firstName = it } ,
                keyboardType = KeyboardType.Text ,
                modifier = modifier
            )
            TextInput(
                label = "Last Name",
                onValueChange = { currentUser.lastName = it } ,
                keyboardType = KeyboardType.Text ,
                modifier = modifier
            )
            TextInput(
                label = "Phone Number",
                onValueChange = { currentUser.phoneNumber = it } ,
                keyboardType = KeyboardType.Phone ,
                icon = Icons.Default.Phone ,
                modifier = modifier
            )
            DropDownInput(
                items = genderOptions,
                onItemClick = { index -> currentUser.gender = genderOptions[ index ] } ,
                modifier = modifier,
                label = "Gender"
            )
            DropDownInput(
                items = bloodGroupOptions,
                onItemClick = { index -> currentUser.bldGrp = bloodGroupOptions[ index ] } ,
                modifier = modifier,
                label = "Blood Group"
            )
            TextInput(
                label = "Weight",
                onValueChange = { currentUser.weight = if( it.isNotEmpty() ) { it.toInt() } else { 0  } } ,
                keyboardType = KeyboardType.Number ,
                icon = Icons.Default.Accessibility ,
                modifier = modifier
            )
            DatePicker(
                modifier = modifier ,
                label = "Select Date of Birth"
            )
            TextInput(
                label = "Height",
                onValueChange = { currentUser.height = if( it.isNotEmpty() ) { it.toFloat() } else { 0.0f } } ,
                keyboardType = KeyboardType.Decimal ,
                icon = Icons.Default.LinearScale ,
                modifier = modifier
            )
            Button(onClick = {
                if( currentUser.validateDetails() ) {
                    CoroutineScope( Dispatchers.IO ).launch {
                        val result = auth.createUserWithEmailAndPassword( currentUser.email , userPassword ).await()
                        println( "User ID : " + result.user?.uid )
                        CurrentUserDetails().apply {
                            createUser( currentUser , result.user?.uid!! )
                        }
                        finish()
                    }
                }
                else {
                    Toast
                        .makeText( context , "Check your details. An invalid detail was found." , Toast.LENGTH_LONG )
                        .show()
                }

            }) {
                Text(text = "Register")
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserPassword( modifier : Modifier ){
        var pass by rememberSaveable{ mutableStateOf( "" ) }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(
            modifier = modifier,
            value = pass,
            singleLine = true,
            onValueChange = {
                pass = it
                userPassword = pass
            } ,
            placeholder = { Text("Password",style = MaterialTheme.typography.labelSmall) } ,
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
            keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password , imeAction = ImeAction.Done),
        )
    }
}