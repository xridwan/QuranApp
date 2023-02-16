package com.xridwan.alquran.domain.repository

import com.xridwan.alquran.domain.Resource
import com.xridwan.alquran.domain.model.Ayat
import com.xridwan.alquran.domain.model.Surat
import kotlinx.coroutines.flow.Flow

interface QuranRepository {
    fun getSurat(): Flow<Resource<List<Surat>>>
    fun getAyat(id: Int): Flow<Resource<List<Ayat>>>
}