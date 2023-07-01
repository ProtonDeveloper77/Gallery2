package com.example.protonGallery.database

import android.net.Uri
import androidx.room.TypeConverter

class UriConverter {

    @TypeConverter
    fun convertStringToUri(string: String): Uri {
        return Uri.parse(string)
    }

    @TypeConverter
    fun convertUriToString(uri: Uri): String {
        return uri.toString()
    }

}
