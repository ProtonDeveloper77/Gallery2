package com.example.protonGallery.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.protonGallery.data.DataProvider
import com.example.protonGallery.data.Media
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch

@Database(entities = [Media::class], version = 1, exportSchema = false)
abstract class GalleryDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao

    companion object {
        @Volatile
        private var INSTANCE: GalleryDatabase? = null

        @OptIn(InternalCoroutinesApi::class, DelicateCoroutinesApi::class)
        fun getDataBase(context: Context): GalleryDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GalleryDatabase::class.java,
                        "GalleryDb"
                    )
                        .allowMainThreadQueries()
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                GlobalScope.launch {
                                    GalleryDatabase.getDataBase(context).mediaDao()
                                        .insertAll(DataProvider.getDataBaseProvider(context = context).getMedia())
                                }
                            }
                        })
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}

class GalleryCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        GlobalScope.launch {
            GalleryDatabase.getDataBase(context).mediaDao()
                .insertAll(DataProvider.getDataBaseProvider(context = context).getMedia())
        }
    }
}
