package com.xridwan.alquran.domain.usecase

import com.xridwan.alquran.domain.Resource
import com.xridwan.alquran.domain.model.Ayat
import com.xridwan.alquran.domain.model.Doa
import com.xridwan.alquran.domain.model.Surat
import kotlinx.coroutines.flow.Flow

interface QuranUseCase {
    fun getSurat(): Flow<Resource<List<Surat>>>
    fun getAyat(id: Int): Flow<Resource<List<Ayat>>>
    fun getDoa(): Flow<Resource<List<Doa>>>
}