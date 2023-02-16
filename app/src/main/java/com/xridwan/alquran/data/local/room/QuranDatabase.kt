package com.xridwan.alquran.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xridwan.alquran.data.local.room.QuranDao
import com.xridwan.data.source.local.entity.AyatEntity
import com.xridwan.alquran.data.local.entity.SuratEntity

@Database(
    entities = [
        SuratEntity::class,
        AyatEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao
}