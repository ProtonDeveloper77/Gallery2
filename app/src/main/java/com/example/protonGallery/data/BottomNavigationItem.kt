package com.example.protonGallery.data

import androidx.compose.ui.graphics.Color
import com.example.protonGallery.screens.Screen

data class BottomNavigationItem(
    val id: Int,
    val title: String,
    val selectedColor: Color,
    val unSelectedColor: Color,
    val screen: Screen ) {
}
