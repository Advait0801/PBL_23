package com.pict.pbl.medswift.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pict.pbl.medswift.screens.history.HistoryScreen
import com.pict.pbl.medswift.screens.prescriptions.PrescriptionsScreen
import com.pict.pbl.medswift.screens.profile.ProfileScreen
import com.pict.pbl.medswift.screens.symptoms.SymptomsActivity
import com.pict.pbl.medswift.ui.theme.MedSwiftTheme
import com.pict.pbl.medswift.viewmodels.HistoryViewModel

class HomeScreen : ComponentActivity() {

    private val historyViewModel : HistoryViewModel by viewModels()

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
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    @Preview
    private fun ActivityUI() {
        // TODO: Improve FAB here
        val context = LocalContext.current
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNav(navController) } ,
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    Intent( context , SymptomsActivity::class.java ).apply {
                        startActivity( this )
                    }
                }) {
                    Row( modifier = Modifier.padding( 8.dp ) ) {
                        Icon( Icons.Default.LocalHospital , contentDescription = "Diagnosis" )
                        Text(text = "Diagnosis" , modifier = Modifier.padding( 8.dp ) )
                    }
                }
            }
        ) { padding ->
            Column( modifier = Modifier.padding( padding )) {
                BottomNavGraph(navController = navController)
            }
        }
    }

    @Composable
    private fun BottomNav( navController: NavController ) {
        // TODO: Improve bottom nav here
        val screens = listOf(
            //BottomNavItem.HomeScreenItem ,
            BottomNavItem.ProfileScreenItem ,
            BottomNavItem.HistoryScreenItem ,
            BottomNavItem.PrescriptionsScreenItem
        )
        NavigationBar(
            containerColor = Color.White ,
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            screens.forEach{ item ->
                NavigationBarItem(
                    selected = currentRoute == item.screenRoute,
                    onClick = {
                        navController.navigate( item.screenRoute ) {
                            navController.graph.startDestinationRoute.let {
                                popUpTo( it!! ) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } ,
                    icon = { Icon(imageVector = item.icon, contentDescription = item.title) } ,
                    label = { Text(text = item.title) }
                )
            }
        }

    }

    @Composable
    private fun BottomNavGraph( navController: NavHostController ) {
        NavHost(navController = navController, startDestination = BottomNavItem.HomeScreenItem.screenRoute ) {
            composable( BottomNavItem.HomeScreenItem.screenRoute ) { RegisterScreen() }
            composable( BottomNavItem.ProfileScreenItem.screenRoute ) { ProfileScreen() }
            composable( BottomNavItem.HistoryScreenItem.screenRoute ) { HistoryScreen( historyViewModel ) }
            composable( BottomNavItem.PrescriptionsScreenItem.screenRoute ) { PrescriptionsScreen() }
        }
    }


    private sealed class BottomNavItem( var title : String ,
                                        var icon : ImageVector ,
                                        var screenRoute : String ) {
        // TODO: Improve bottom nav items - title and icon
        object ProfileScreenItem: BottomNavItem( "Profile" , Icons.Default.Person , "profile" )
        object HomeScreenItem: BottomNavItem( "Home" , Icons.Default.Home , "home" )
        object HistoryScreenItem: BottomNavItem( "History" , Icons.Default.History , "history" )
        object PrescriptionsScreenItem : BottomNavItem( "Prescriptions" , Icons.Default.Report , "prescriptions" )
    }

}