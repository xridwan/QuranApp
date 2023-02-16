package com.xridwan.alquran.di

import androidx.room.Room
import com.xridwan.alquran.data.local.room.QuranDatabase
import com.xridwan.alquran.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            QuranDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }
    single {
        get<QuranDatabase>().quranDao()
    }
}