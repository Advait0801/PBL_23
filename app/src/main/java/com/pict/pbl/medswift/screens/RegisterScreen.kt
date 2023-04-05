package com.pict.pbl.medswift.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pict.pbl.medswift.auth.CurrentUser
import com.pict.pbl.medswift.data.User
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
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


    @Preview
    @Composable
    private fun ActivityUI() {
        // TODO: Add register screen UI here
        LazyColumn(){
            item {
                Text(
                    text = "History" ,
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
            }
            item{
                UserFirstName(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp, top = 16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                UserLastName(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                UserPhoneNumber(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                UserEmailId(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                UserPassword(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                UserGender(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                UserBloodGroup(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                UserHeight(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                UserWeight(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                Button(onClick = {
                    CoroutineScope( Dispatchers.IO ).launch {
                        val result = auth.createUserWithEmailAndPassword( currentUser.email , password ).await()
                        println( "User ID : " + result.user?.uid )
                        CurrentUser().apply {
                            createUser( currentUser , result.user?.uid!! )
                        }
                    }

                }) {
                    Text(text = "Register")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserFirstName(
        modifier: Modifier = Modifier
    ){
        var firstName by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = modifier,
            value = firstName,
            onValueChange = {
                currentUser.firstName = it
                firstName = it
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            placeholder = { Text(text = "First name")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next)}),

        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserLastName(
        modifier: Modifier = Modifier
    ){
        var lastName by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = modifier,
            value = lastName,
            onValueChange = {
                    it ->
                currentUser.lastName = it
                lastName = it
            },
            placeholder = { Text(text = "Last name")},
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
    private fun UserPhoneNumber(
        modifier: Modifier = Modifier
    ){
        var phoneNumber by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = modifier,
            value = phoneNumber,
            onValueChange = {
                    it ->
                currentUser.phoneNumber = it
                phoneNumber = it
            },
            placeholder = { Text(text = "Phone Number")},
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next)})
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserEmailId(
        modifier: Modifier = Modifier
    ){
        var emailId by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = modifier,
            value = emailId,
            onValueChange = {
                    it ->
                currentUser.email = it
                emailId = it
            },
            placeholder = { Text(text = "Email Id")},
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next)})
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserPassword( modifier : Modifier ){
        var password by rememberSaveable{ mutableStateOf( "" ) }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(
            modifier = modifier,
            value = password,
            singleLine = true,
            onValueChange = { it ->
                password = it
                this@RegisterScreen.password = it
                            } ,
            placeholder = { Text("Password") } ,
            leadingIcon = { Icon( imageVector = Icons.Default.Lock , contentDescription = "Password" ) } ,
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