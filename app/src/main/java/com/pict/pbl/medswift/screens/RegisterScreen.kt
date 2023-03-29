package com.pict.pbl.medswift.screens

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme

class RegisterScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedSwiftTheme() {
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
    private fun ActivityUI() {
        // TODO: Add register screen UI here
        LazyColumn(){
            item{
                UserFirstName(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
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
            onValueChange ={
                it -> firstName = it
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
            onValueChange ={
                    it -> lastName = it
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
    private fun UserDOB(
        modifier: Modifier = Modifier
    ){
        var lastName by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        TextField(
            modifier = modifier,
            value = lastName,
            onValueChange ={
                    it -> lastName = it
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
    


}