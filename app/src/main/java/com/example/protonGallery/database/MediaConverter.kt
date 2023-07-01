package com.example.protonGallery.database

import androidx.room.TypeConverter
import com.example.protonGallery.data.Media

class MediaConverter {

    @TypeConverter
    fun toMedia(id: Int, albumName: String, photoUri: String): Media {
        return Media(id, albumName,photoUri)
    }
}