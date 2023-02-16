package com.xridwan.alquran.presenter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.xridwan.alquran.data.preference.HistoryPreference
import com.xridwan.alquran.domain.usecase.QuranUseCase

class SurahViewModel(
    prefs: HistoryPreference,
    private val quranUseCase: QuranUseCase
) : ViewModel() {

    val mPrefs = prefs
    fun surat() = quranUseCase.getSurat().asLiveData()
}