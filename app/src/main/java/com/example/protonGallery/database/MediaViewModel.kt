package com.example.protonGallery.database

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.protonGallery.data.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MediaViewModel(context: Context): ViewModel() {

    var allMedia = MutableLiveData<List<Media>>()
    var mediaRepository: MediaRepository = MediaRepository(GalleryDatabase.getDataBase(context).mediaDao())

    init {
        mediaRepository = MediaRepository(GalleryDatabase.getDataBase(context).mediaDao())
        allMedia.value = mediaRepository.allMedias.value
    }

    suspend fun deleteMedia(media: Media) = viewModelScope.launch(Dispatchers.IO) {
        mediaRepository.delete(media)
    }

    suspend fun updateMedia(media: Media) = viewModelScope.launch(Dispatchers.IO) {
        mediaRepository.update(media)
    }

    suspend fun insertMedia(media: Media) = viewModelScope.launch(Dispatchers.IO) {
        mediaRepository.insert(media)
    }
}