package com.pict.pbl.medswift.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pict.pbl.medswift.data.User
import com.pict.pbl.medswift.data.UserDiagnosis
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme

@Composable
fun HistoryScreen( history : List<UserDiagnosis> = emptyList() ) {
    MedSwiftTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            ScreenUI( history )
        }
    }
}

@Composable
private fun ScreenUI( history : List<UserDiagnosis> ) {
    // TODO: Implement history screen here
}

