package com.example.axxesschallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageDBEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}