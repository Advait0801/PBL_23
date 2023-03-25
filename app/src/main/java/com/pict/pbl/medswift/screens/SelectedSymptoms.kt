package com.pict.pbl.medswift.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
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
        SelectedSymptomsList( symptomsViewModel )
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
private fun SelectedSymptomsList( symptomsViewModel: SymptomsViewModel ) {
    Log.e( "APP" , "Selected Shown" )
    val selectedItems by symptomsViewModel.selectedSymptomsList.observeAsState()
    Column {
        AnimatedVisibility(visible = (selectedItems ?: ArrayList()).size != 0) {
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
                itemsIndexed( symptomsViewModel.selectedSymptomsList.value ?: ArrayList() ) { index, symptom ->
                    Log.e( "APP" , "Selected Shown" )
                    SymptomItem(
                        index ,
                        symptomsViewModel
                    )
                }
            }
        }
    }

}

@Composable
private fun SymptomsList( symptomsViewModel: SymptomsViewModel ) {
    val searchQuery by searchText.observeAsState()
    Column {
        LazyColumn( modifier = Modifier.fillMaxSize() ) {
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
            itemsIndexed( symptomsViewModel.symptomsList.value ?: ArrayList() ) { index , symptom ->
                if( symptom.laytext.contains( searchQuery ?: "" ) ) {
                    SymptomItem(
                        index ,
                        symptomsViewModel
                    )
                }
            }
        }
    }

}

@Composable
private fun SymptomItem( index : Int , symptomsViewModel: SymptomsViewModel ) {
    // TODO: Add necessary styling to symptom text (here)
    Surface( modifier = Modifier
        .background(color = Color.White)
    ) {
        val symptoms = symptomsViewModel.symptomsList.value ?: ArrayList()
        Text(text = symptoms[ index ].text ,
            fontSize = 14.sp ,
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(16.dp)
                .clickable(enabled = true) {
                    symptomsViewModel.clickedSymptomIndex = index
                    symptomsViewModel.navController?.navigate( "inputSymptoms" )
                    Log.e("APP", "Clicked")
                    symptoms[index].isUserSelected = true
                    symptomsViewModel.addNewSelectedSymptom( symptoms[index] )
                }
        )
    }
}