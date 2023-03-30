package com.pict.pbl.medswift.screens.user_auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
