package com.jayesh.shadi.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProfileTable::class],version = 1,exportSchema = false)
public abstract class AppDatabase: RoomDatabase() {
    public abstract fun profileTableDao():ProfileTableDao

}