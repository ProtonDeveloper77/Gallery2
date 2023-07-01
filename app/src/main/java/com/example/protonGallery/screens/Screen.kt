package com.example.protonGallery.screens

sealed class Screen(val route: String, val parameter: String) {
    object AlbumScreen : Screen("album", "albumName")
    object MediaScreen : Screen("media", "")
    object CategoryScreen : Screen("category", "categoryName")
}
