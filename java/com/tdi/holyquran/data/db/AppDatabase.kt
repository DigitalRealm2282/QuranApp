package com.tdi.holyquran.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tdi.holyquran.data.db.dao.AyahDao
import com.tdi.holyquran.data.db.entities.Ayah
import com.tdi.holyquran.data.db.entities.Sura

@Database(
    entities = [Sura::class, Ayah::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAyahDao(): AyahDao
}