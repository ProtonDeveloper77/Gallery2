package com.example.protonGallery.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.protonGallery.data.Media

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(media: Media)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(media: List<Media>)

    @Query("SELECT * FROM photo_table")
    fun getMedia():LiveData<List<Media>>

    @Query("SELECT MIN(id) AS id, album_name, photo_uri FROM photo_table GROUP BY album_name")
    fun getAlbumList(): List<Media>

    @Query("SELECT album_name, COUNT(*) FROM photo_table WHERE album_name = :albName GROUP BY album_name")
    fun getNoOfPhotos(albName: String):Int

    @Delete
    suspend fun delete(media: Media)

    @Update
    suspend fun update(media: Media)
}