package com.abhi.alleimageviewer.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.abhi.alleimageviewer.states.Screens

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Photos",
                icon = Icons.Filled.Face,
                route = Screens.Pictures.route
            ),
            BottomNavigationItem(
                label = "Details",
                icon = Icons.Filled.Info,
                route = Screens.Details.route
            ),
        )
    }
}