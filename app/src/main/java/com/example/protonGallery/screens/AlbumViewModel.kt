package com.example.protonGallery.screens

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.protonGallery.data.Media
import com.example.protonGallery.database.GalleryDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AlbumViewModel : ViewModel() {

    private val _albumState = MutableStateFlow(listOf<Media>())
    val albumState = _albumState

    fun fetchData(context: Context) {
        viewModelScope.launch {
            _albumState.value = GalleryDatabase.getDataBase(context).mediaDao().getAlbumList()
        }
    }
}