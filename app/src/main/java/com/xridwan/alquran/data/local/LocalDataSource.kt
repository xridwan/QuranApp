package com.xridwan.alquran.data.local

import com.xridwan.alquran.data.local.entity.SuratEntity
import com.xridwan.alquran.data.local.room.QuranDao
import com.xridwan.data.source.local.entity.AyatEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val quranDao: QuranDao
) {
    fun getSurat(): Flow<List<SuratEntity>> = quranDao.getSurat()

    suspend fun insertSurat(suratList: List<SuratEntity>) = quranDao.insertSurat(suratList)

    fun getAyat(id: Int): Flow<List<AyatEntity>> = quranDao.getAyat(id)

    suspend fun insertAyat(ayatList: List<AyatEntity>) = quranDao.insertAyat(ayatList)
}