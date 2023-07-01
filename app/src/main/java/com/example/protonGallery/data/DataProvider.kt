package com.example.protonGallery.data

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.protonGallery.R
import com.example.protonGallery.screens.Screen
import com.example.protonGallery.ui.theme.Blue
import com.example.protonGallery.ui.theme.DarkBlue
import com.example.protonGallery.ui.theme.DarkOrange
import com.example.protonGallery.ui.theme.DarkPink
import com.example.protonGallery.ui.theme.Orange
import com.example.protonGallery.ui.theme.Pink
import kotlinx.coroutines.InternalCoroutinesApi

class DataProvider(private val context: Context) {

    private var media: MutableList<Media> = mutableListOf()

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: DataProvider? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDataBaseProvider(context: Context): DataProvider {
            if (INSTANCE == null) {
                kotlinx.coroutines.internal.synchronized(this) {
                    INSTANCE = DataProvider(context)
                }
            }
            return INSTANCE!!
        }
    }

    private fun fertilizeDatabase() {
        val imageColumns = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"

        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageColumns,
            null,
            null,
            sortOrder
        )
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                media.add(
                    Media(
                        id.toInt(),
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)),
                        Uri.withAppendedPath(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id.toString()
                        ).toString()
                    )
                )
            }
        }
    }

    fun getMedia(): List<Media> {
        if (media.isEmpty())
            fertilizeDatabase()
        return media
    }

    fun getMedia(uri: Uri, context: Context): Media {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
        var imageId = 0
        var albumName = ""
        uri.let {
            context.contentResolver.query(it, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                    val albumIndex =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

                    imageId = cursor.getLong(idIndex).toInt()
                    albumName = cursor.getString(albumIndex)
                }
            }
        }
        return Media(imageId, albumName, uri.toString())
    }

    public fun getBottomNavigationItemList(context: Context): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(R.drawable.album, context.getString(R.string.album), Blue, DarkBlue, Screen.AlbumScreen),
            BottomNavigationItem(R.drawable.favourite, context.getString(R.string.favourite), Pink, DarkPink, Screen.MediaScreen),
            BottomNavigationItem(R.drawable.category, context.getString(R.string.category), Orange, DarkOrange, Screen.CategoryScreen)
        )
    }

    public fun getDrawerNavigationItemList(context: Context): List<NavigationDrawerItem> {
        return listOf(
            NavigationDrawerItem(R.drawable.video, context.getString(R.string.video)),
            NavigationDrawerItem(R.drawable.google_photos, context.getString(R.string.google_photo)),
            NavigationDrawerItem(R.drawable.onedrive, context.getString(R.string.one_drive)),
            NavigationDrawerItem(R.drawable.settings, context.getString(R.string.settings)),
            NavigationDrawerItem(R.drawable.id_card, context.getString(R.string.about_us)),
            NavigationDrawerItem(R.drawable.feedback, context.getString(R.string.feedback))
        )
    }

    public fun getCategoryList(context: Context): List<Category> {
        return listOf(Category(R.drawable.nature, context.getString(R.string.nature)),
            Category(R.drawable.travel, context.getString(R.string.travel)),
            Category(R.drawable.events, context.getString(R.string.events)),
            Category(R.drawable.food, context.getString(R.string.food)),
            //Category(R.drawable.visit, context.getString(R.string.visit)),
            Category(R.drawable.sports, context.getString(R.string.sports)),
            //Category(R.drawable.pets, context.getString(R.string.pets))
        )
    }
}