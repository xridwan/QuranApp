package com.xridwan.alquran.presenter.doa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.alquran.domain.usecase.QuranUseCase

class DoaViewModel(
    private val quranUseCase: QuranUseCase
) : ViewModel() {

    fun doa() = quranUseCase.getDoa().asLiveData()
}