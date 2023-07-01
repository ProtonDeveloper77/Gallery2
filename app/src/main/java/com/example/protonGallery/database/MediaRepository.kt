package com.example.protonGallery.database

import com.example.protonGallery.data.Media

class MediaRepository(private val mediaDao: MediaDao) {
    val allMedias = mediaDao.getMedia()
    val allAlbum = mediaDao.getAlbumList()

    suspend fun insert(Media: Media) {
        mediaDao.insert(Media)
    }

    suspend fun delete(Media: Media) {
        mediaDao.delete(Media)
    }

    suspend fun update(Media: Media) {
        mediaDao.update(Media)
    }
}