package com.pict.pbl.medswift.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.pict.pbl.medswift.data.UserDiagnosis

class HistoryViewModel : ViewModel() {

    var clickedDiagnosisItem : UserDiagnosis? = null
    var historyNavController : NavController? = null

}