package com.xridwan.alquran.presenter.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.alquran.data.preference.HistoryPreference
import com.xridwan.alquran.domain.usecase.QuranUseCase

class DetailViewModel(
    prefs: HistoryPreference,
    private val quranUseCase: QuranUseCase
) : ViewModel() {

    val mPrefs = prefs
    fun ayat(id: Int) = quranUseCase.getAyat(id).asLiveData()
}