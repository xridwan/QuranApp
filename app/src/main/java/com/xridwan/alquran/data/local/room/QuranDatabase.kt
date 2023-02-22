package com.xridwan.alquran.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xridwan.alquran.data.local.entity.DoaEntity
import com.xridwan.alquran.data.local.entity.SuratEntity
import com.xridwan.alquran.data.local.entity.AyatEntity

@Database(
    entities = [
        SuratEntity::class,
        AyatEntity::class,
        DoaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao
}