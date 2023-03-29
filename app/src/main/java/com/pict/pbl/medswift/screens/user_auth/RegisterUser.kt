package com.pict.pbl.medswift.screens.user_auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.pict.pbl.medswift.login.LoginManager
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.viewmodels.LoginViewModel

@Composable
fun RegisterUserScreen( loginViewModel: LoginViewModel ) {
    MedSwiftTheme {
        Surface(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize() ,
        ) {
            ScreenUI( loginViewModel )
        }
    }
}

@Composable
private fun ScreenUI( loginViewModel: LoginViewModel ) {
    // TODO: Create register screen UI here, take name, weight, height etc.
}
