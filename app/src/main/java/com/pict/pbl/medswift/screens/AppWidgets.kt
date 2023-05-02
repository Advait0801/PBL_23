package com.pict.pbl.medswift.ui.theme

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun ScreenTitle( title : String , icon : ImageVector? = null ) {
    if ( icon != null ) {
        Row( verticalAlignment = Alignment.CenterVertically ){
            Icon(imageVector = icon, contentDescription = title , modifier = Modifier.padding( start = 24.dp ) )
            Text(
                text = title ,
                color = Color.Black ,
                style = MaterialTheme.typography.displayLarge ,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            )
        }
    }
    else {
        Text(
            text = title ,
            color = Color.Black ,
            style = MaterialTheme.typography.displayLarge ,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        )
    }
    Divider(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .width(1.dp)
    )
}

@Composable
fun ScreenTitleWithoutDivider( title : String ) {
    Text(
        text = title ,
        color = Color.Black ,
        style = MaterialTheme.typography.displayLarge ,
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    label : String,
    onValueChange : ( (String) -> Unit ),
    modifier: Modifier = Modifier ,
    keyboardType: KeyboardType = KeyboardType.Text,
    icon : ImageVector? = null
) {
    var inputString by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = modifier,
        value = inputString,
        shape = RoundedCornerShape( 16.dp ) ,
        onValueChange = {
            onValueChange( it )
            inputString = it
        },
        textStyle = MaterialTheme.typography.labelLarge ,
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            textColor = Color.Black ,
            containerColor = MaterialTheme.colorScheme.secondaryContainer ,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        label = { Text(text = label , style = MaterialTheme.typography.labelSmall ) },
        singleLine = true,
        leadingIcon = if( icon != null ) { {Icon(imageVector = icon, contentDescription = label)} } else null ,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next)}),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownInput(
    modifier : Modifier = Modifier,
    items : List<String> ,
    onItemClick : ( (Int) -> Unit ) ,
    label : String ,
) {
    var expanded by remember{ mutableStateOf(false) }
    var selectedText by remember{ mutableStateOf( items[0]) }
    Column( modifier = modifier ){
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer , RoundedCornerShape(16.dp)),
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle.Default.copy( fontSize = 20.sp ) ,
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    textColor = Color.Black ,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer ,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                label = { Text(text = label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach{ item ->
                    DropdownMenuItem(
                        text = { Text(text = item , style = MaterialTheme.typography.labelSmall) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onItemClick( items.indexOf( item ) )
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun BubbleText( text : String ,
                modifier: Modifier = Modifier ,
                fontSize : Int = 18  ) {
    Box( modifier = Modifier
        .padding(8.dp)
        .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp)) )  {
        Text(
            text = text ,
            modifier = modifier ,
            textAlign = TextAlign.Start ,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker( modifier: Modifier = Modifier , label : String ){
    val mContext = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    val date = remember { mutableStateOf( "$day/${month+1}/$year" ) }
    val datePickerDialog = DatePickerDialog(
        mContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, year, month, day
    )
    Row( modifier = modifier ) {
        TextField(
            value = date.value,
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle.Default.copy( fontSize = 20.sp ) ,
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                textColor = Color.Black ,
                containerColor = MaterialTheme.colorScheme.secondaryContainer ,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            label = { Text(text = label) },
            enabled = false,
            modifier = Modifier
                .weight(1.0f)
                .padding(16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer , RoundedCornerShape(16.dp))
                .clickable {
                    datePickerDialog.show()
                }
        )
    }
}

