package com.pict.pbl.medswift.screens.symptoms

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.pict.pbl.medswift.data.AnalyzeSymptom
import com.pict.pbl.medswift.data.Symptom
import com.pict.pbl.medswift.data.SymptomChoiceType
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
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
        .padding(32.dp) ){
        if( symptomsViewModel.symptomsList.value != null ) {
            val symptom = symptomsViewModel.clickedSymptom!!
            Text( text = symptom.laytext )
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
    // TODO: Stylize integer-input (Ex. BMI)
    var doubleValue by remember{ mutableStateOf( symptom.min ) }
    Row{
        Text(
            text = "Enter value" ,
            modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth()
        )
        BasicTextField(
            value = doubleValue.toString(),
            onValueChange = {
                doubleValue = it.toDouble()
                symptomsViewModel.addNewAnalyzeSymptom(
                    AnalyzeSymptom(
                        symptom.name,
                        doubleValue.toString()
                    )
                )
            } ,
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun DoubleInput(symptom : Symptom, symptomsViewModel: SymptomsViewModel) {
    // TODO: Stylize double-input (Ex. BMI)
    var doubleValue by remember{ mutableStateOf( symptom.min ) }
    Row{
        Text(
            text = "Enter value" ,
            modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth()
        )
        BasicTextField(
            value = doubleValue.toString(),
            onValueChange = {
                doubleValue = it.toDouble()
                symptomsViewModel.addNewAnalyzeSymptom(
                    AnalyzeSymptom(
                        symptom.name,
                        doubleValue.toString()
                    )
                )
            } ,
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun CategoricalInput(symptom : Symptom, symptomsViewModel: SymptomsViewModel) {
    // TODO: Stylize double-input (Ex. Systolic blood pressure)
    val options = symptom.choices!!.map { it.text }
    var selectedItem by remember{ mutableStateOf(options[0]) }
    Column(modifier = Modifier.selectableGroup()) {
        options.forEach { label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
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
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.padding(end = 16.dp),
                    selected = (selectedItem == label),
                    onClick = null
                )
                Text(text = label)
            }
        }
    }

}