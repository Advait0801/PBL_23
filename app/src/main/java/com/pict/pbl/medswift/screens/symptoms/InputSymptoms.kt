package com.pict.pbl.medswift.screens.symptoms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pict.pbl.medswift.data.AnalyzeSymptom
import com.pict.pbl.medswift.data.Symptom
import com.pict.pbl.medswift.data.SymptomChoiceType
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.ui.theme.TextInput
import com.pict.pbl.medswift.viewmodels.SymptomsViewModel

@Composable
fun InputSymptomsScreen( symptomsViewModel: SymptomsViewModel) {
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
    Column( modifier = Modifier
        .fillMaxSize()
        .padding(16.dp) ){
        if( symptomsViewModel.symptomsList.value != null ) {
            val symptom = symptomsViewModel.clickedSymptom!!
            Text(
                text = symptom.laytext ,
                modifier = Modifier.padding( 16.dp ) ,
                fontSize = 18.sp
            )
            Text(
                text = symptom.text ,
                modifier = Modifier.padding( 16.dp ) ,
                style = MaterialTheme.typography.labelSmall
            )
            when (symptom.type) {
                SymptomChoiceType.integer -> {
                    IntegerInput(symptom = symptom, symptomsViewModel = symptomsViewModel)
                }
                SymptomChoiceType.double -> {
                    DoubleInput(symptom = symptom, symptomsViewModel = symptomsViewModel)
                }
                SymptomChoiceType.categorical -> {
                    CategoricalInput(symptom = symptom, symptomsViewModel)
                }
            }
        }
    }
}

@Composable
private fun IntegerInput(symptom : Symptom, symptomsViewModel: SymptomsViewModel) {
    Row{
        TextInput(
            label = "Enter value",
            onValueChange = {
                symptomsViewModel.addNewAnalyzeSymptom(
                    AnalyzeSymptom(
                        symptom.name,
                        it
                    )
                )
            } ,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth() ,
            keyboardType = KeyboardType.Number ,
            icon = Icons.Default.Dialpad
        )
    }
}

@Composable
private fun DoubleInput(symptom : Symptom, symptomsViewModel: SymptomsViewModel) {
    Row{
        TextInput(
            label = "Enter value",
            onValueChange = {
                symptomsViewModel.addNewAnalyzeSymptom(
                    AnalyzeSymptom(
                        symptom.name,
                        it
                    )
                )
            } ,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth() ,
            keyboardType = KeyboardType.Decimal ,
            icon = Icons.Default.Numbers
        )
    }
}

@Composable
private fun CategoricalInput(symptom : Symptom, symptomsViewModel: SymptomsViewModel) {
    val options = symptom.choices!!.map { it.text }
    var selectedItem by remember{ mutableStateOf(options[0]) }
    Column(modifier = Modifier.selectableGroup()) {
        options.forEach { label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (selectedItem == label),
                        onClick = {
                            selectedItem = label
                            symptomsViewModel.addNewAnalyzeSymptom(
                                AnalyzeSymptom(
                                    symptom.name,
                                    options
                                        .indexOf(selectedItem)
                                        .toString()
                                )
                            )
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.padding(end = 16.dp),
                    selected = (selectedItem == label),
                    onClick = null ,
                )
                Text(text = label)
            }
        }
    }

}