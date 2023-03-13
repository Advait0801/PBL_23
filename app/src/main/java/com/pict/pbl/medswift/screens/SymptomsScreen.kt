package com.pict.pbl.medswift.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pict.pbl.medswift.data.Symptom
import com.pict.pbl.medswift.symptoms.SymptomsJSONReader
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme

class SymptomsScreen : ComponentActivity() {
    
    private lateinit var symptoms : ArrayList<Symptom>

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

        SymptomsJSONReader( this ).apply {
            symptoms = parseSymptoms()
        }
        
    }

    @Preview
    @Composable
    private fun ActivityUI() {
        LazyColumn( modifier = Modifier.fillMaxSize() ) {
            items( symptoms ) {
                SymptomItem(symptom = it)
            }
        }
    }
    
    @Composable
    private fun SymptomItem( symptom: Symptom ) {
        // TODO: Add necessary styling to symptom text (here)
        Surface( modifier = Modifier
            .background( color = Color.White )
            .clickable {
                // TODO: Handle symptom click here
            }
        ) {
            Text(text = symptom.laytext ,
                modifier = Modifier
                    .fillMaxSize()
                    .padding( 8.dp )
            )
        }
    }
    

}