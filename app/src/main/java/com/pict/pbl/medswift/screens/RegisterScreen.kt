package com.pict.pbl.medswift.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pict.pbl.medswift.auth.CurrentUserDetails
import com.pict.pbl.medswift.data.User
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.ui.theme.ScreenTitle
import com.pict.pbl.medswift.ui.theme.TextInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterScreen : ComponentActivity() {

    private val currentUser = User()
    private val auth = Firebase.auth
    private var password = ""

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

    private val modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp).fillMaxWidth()

    @Preview
    @Composable
    private fun ActivityUI() {
        // TODO: Add register screen UI here
        Column( modifier = Modifier.verticalScroll( rememberScrollState() ) ){
            ScreenTitle(title = "Register", icon = Icons.Default.PersonAdd)
            TextInput(
                label = "First Name",
                onValueChange = { currentUser.firstName = it } ,
                modifier = modifier
            )
            TextInput(
                label = "Last Name",
                onValueChange = { currentUser.lastName = it } ,
                modifier = modifier
            )
            TextInput(
                label = "Phone Number",
                onValueChange = { currentUser.phoneNumber = it } ,
                keyboardType = KeyboardType.Phone ,
                icon = Icons.Default.Phone ,
                modifier = modifier
            )
            TextInput(
                label = "Email Address",
                onValueChange = { currentUser.email = it } ,
                keyboardType = KeyboardType.Email ,
                icon = Icons.Default.Email ,
                modifier = modifier
            )
            UserGender( modifier )
            UserBloodGroup( modifier )
            UserHeight( modifier )
            UserWeight( modifier )
            Button(onClick = {
                CoroutineScope( Dispatchers.IO ).launch {
                    val result = auth.createUserWithEmailAndPassword( currentUser.email , password ).await()
                    println( "User ID : " + result.user?.uid )
                    CurrentUserDetails().apply {
                        createUser( currentUser , result.user?.uid!! )
                    }
                }
            }) {
                Text(text = "Register")
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserGender(
        modifier: Modifier = Modifier
    ){
        var gender by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = modifier,
            value = gender,
            onValueChange = {
                    it ->
                currentUser.gender = it
                gender = it
            },
            placeholder = { Text(text = "Gender")},
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next)})
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserBloodGroup(
        modifier: Modifier = Modifier
    ){
        var bloodGroup by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = modifier,
            value = bloodGroup,
            onValueChange = {
                    it ->
                currentUser.bldGrp = it
                bloodGroup = it
            },
            placeholder = { Text(text = "Blood Group")},
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next)})
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserWeight(
        modifier: Modifier = Modifier
    ){
        var weight by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = modifier,
            value = weight,
            onValueChange = {
                    it ->
                currentUser.weight = it.toInt()
                weight = it
            },
            placeholder = { Text(text = "Weight")},
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next)})
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserHeight(
        modifier: Modifier = Modifier
    ){
        var height by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = modifier,
            value = height,
            onValueChange = {
                    it ->
                currentUser.height = it.toFloat()
                height = it
            },
            placeholder = { Text(text = "Height")},
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next)})
        )
    }
}