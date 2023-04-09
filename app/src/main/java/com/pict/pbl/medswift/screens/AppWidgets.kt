package com.pict.pbl.medswift.ui.theme

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenTitle( title : String , icon : ImageVector? = null ) {
    if ( icon != null ) {
        Row( verticalAlignment = Alignment.CenterVertically ){
            Icon(imageVector = icon, contentDescription = title , modifier = Modifier.padding( start = 24.dp ) )
            Text(
                text = title ,
                color = Color.Black ,
                fontWeight = FontWeight.Bold ,
                fontSize = 22.sp ,
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
            fontWeight = FontWeight.Bold ,
            fontSize = 22.sp ,
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
        fontWeight = FontWeight.Bold ,
        fontSize = 22.sp ,
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
        label = { Text(text = label ) },
        singleLine = true,
        leadingIcon = if( icon != null ) { {Icon(imageVector = icon, contentDescription = label)} } else null ,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next)}),
    )
}


@Composable
fun BubbleText( text : String ,
                modifier: Modifier = Modifier ,
                fontSize : Int = 18 ,
                textStyle: TextStyle = TextStyle( fontWeight = FontWeight.Bold ) ) {
    Box( modifier = Modifier
        .padding(8.dp)
        .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp)) )  {
        Text(
            text = text ,
            modifier = modifier ,
            style = textStyle ,
            textAlign = TextAlign.Start ,
            fontSize = fontSize.sp
        )
    }
}

