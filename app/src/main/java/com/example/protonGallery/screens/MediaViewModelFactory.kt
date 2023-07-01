package com.example.protonGallery.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.protonGallery.database.MediaViewModel

class MediaViewModelFactory(private val context: Context):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MediaViewModel(context) as T
    }
}