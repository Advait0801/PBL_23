package com.pict.pbl.medswift.screens.symptoms

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.pict.pbl.medswift.data.Symptom
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.viewmodels.SymptomsViewModel

private val searchText = MutableLiveData( "" )

@Composable
fun SelectedSymptomsScreen( symptomsViewModel: SymptomsViewModel ) {
    MedSwiftTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            ScreenUI( symptomsViewModel )
        }
    }
}

@Composable
private fun ScreenUI( symptomsViewModel: SymptomsViewModel ) {
    Column{
        SymptomsSearch()
        SymptomsList( symptomsViewModel )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SymptomsSearch() {
    var searchQuery by rememberSaveable{ mutableStateOf( "" ) }
    OutlinedTextField(
        value = searchQuery ,
        onValueChange = {
            searchText.value = it
            searchQuery = it
        } ,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth() ,
        singleLine = true ,
        placeholder = { Text("Search Symptoms...") } ,
        leadingIcon = { Icon( imageVector = Icons.Default.Search , contentDescription = "Search Symptoms" ) }
    )
}


@Composable
private fun SymptomsList( symptomsViewModel: SymptomsViewModel ) {
    Log.e( "APP" , "Selected Shown" )
    val searchQuery by searchText.observeAsState()
    val selectedSymptoms by symptomsViewModel.selectedSymptomsList.observeAsState()
    val allSymptoms by symptomsViewModel.symptomsList.observeAsState()
    Column {
        LazyColumn( modifier = Modifier
            .padding(bottom = 32.dp)
            .fillMaxWidth() ) {
            item {
                Text(
                    text = "Selected Symptoms" ,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth() ,
                    style = TextStyle( fontWeight = FontWeight.Bold ) ,
                    textAlign = TextAlign.Start ,
                    fontSize = 14.sp
                )
            }
            itemsIndexed( selectedSymptoms ?: ArrayList() ) { index, symptom ->
                Log.e( "APP" , "Selected Symptoms Changed Shown" )
                SelectedSymptomItem(
                    symptom ,
                    symptomsViewModel
                )
            }
            item {
                Text(
                    text = "All Symptoms" ,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth() ,
                    style = TextStyle( fontWeight = FontWeight.Bold ) ,
                    textAlign = TextAlign.Start ,
                    fontSize = 12.sp
                )
            }
            itemsIndexed( allSymptoms ?: ArrayList() ) { index , symptom ->
                if( symptom.laytext.contains( searchQuery ?: "" ) ) {
                    SymptomItem(
                        symptom ,
                        symptomsViewModel
                    )
                }
            }
        }

    }

}

@Composable
private fun SelectedSymptomItem( symptom: Symptom , symptomsViewModel: SymptomsViewModel ) {
    Surface( modifier = Modifier
        .background(color = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = symptom.text ,
                fontSize = 14.sp ,
                modifier = Modifier
                    .background(Color.White)
                    .weight(1.0f)
                    .padding(16.dp)
            )
            Icon(
                Icons.Default.Person,
                contentDescription = "Remove Selection" ,
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
                    .clickable {
                        symptom.isUserSelected = false
                        symptomsViewModel.removeSelectedSymptom(symptom)
                    }
            )
        }
    }
}

@Composable
private fun SymptomItem( symptom : Symptom , symptomsViewModel: SymptomsViewModel ) {
    // TODO: Add necessary styling to symptom text (here)
    Surface( modifier = Modifier
        .background(color = Color.White)
    ) {
        Row {
            Text(text = symptom.text ,
                fontSize = 14.sp ,
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
                    .clickable(enabled = true) {
                        Log.e("APP", "Clicked -> ${symptom.text}")
                        symptom.isUserSelected = true
                        symptomsViewModel.clickedSymptom = symptom
                        symptomsViewModel.addNewSelectedSymptom(symptom)
                        symptomsViewModel.navController?.navigate("inputSymptoms")
                    }
            )
        }
    }
}