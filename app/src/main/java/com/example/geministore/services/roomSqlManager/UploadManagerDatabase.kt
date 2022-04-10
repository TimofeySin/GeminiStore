package com.example.geministore.services.roomSqlManager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UploadManager::class], version = 1, exportSchema = false)
abstract class UploadManagerDatabase : RoomDatabase() {
    abstract val uploadManagerDAO: UploadManagerDAO

    companion object {
        @Volatile
        private var INSTANCE: UploadManagerDatabase? = null
        fun getInstance(context: Context): UploadManagerDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UploadManagerDatabase::class.java,
                        "upload_manager_table")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}