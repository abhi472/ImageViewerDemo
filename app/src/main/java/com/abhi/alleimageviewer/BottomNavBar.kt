package com.abhi.alleimageviewer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abhi.alleimageviewer.screens.DetailScreen
import com.abhi.alleimageviewer.screens.PictureScreen

@Composable
fun BottomNavigationBar(viewModel: MainViewModel) {
    val state = viewModel.uiState.collectAsState().value

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.route == currentDestination?.route,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {


                            if(state.files.isNotEmpty() && navigationItem.route == Screens.Details.route) {
                                viewModel.loadLabels(state.files[state.selectedFile], navController.context)
                                viewModel.loadOcr(state.files[state.selectedFile], navController.context)
                            }
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Pictures.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
            composable(Screens.Pictures.route) {
                PictureScreen(
                    viewModel,
                    navController
                )
            }
            composable(Screens.Details.route) {
                DetailScreen(
                    viewModel,
                    navController
                )
            }
        }
    }
}