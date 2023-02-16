package com.xridwan.alquran.domain.usecase

import com.xridwan.alquran.domain.Resource
import com.xridwan.alquran.domain.model.Ayat
import com.xridwan.alquran.domain.model.Surat
import com.xridwan.alquran.domain.repository.QuranRepository
import kotlinx.coroutines.flow.Flow

class QuranUseCaseImpl(
    private val quranRepository: QuranRepository
) : QuranUseCase {
    override fun getSurat(): Flow<Resource<List<Surat>>> {
        return quranRepository.getSurat()
    }

    override fun getAyat(id: Int): Flow<Resource<List<Ayat>>> {
        return quranRepository.getAyat(id)
    }
}