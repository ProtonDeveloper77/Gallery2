package com.example.protonGallery.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(
    tableName = "photo_table",
    indices = [Index("album_name")]
)
data class Media(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "album_name")
    val albumName: String,
    @ColumnInfo(name = "photo_uri")
    val uri: String
)
