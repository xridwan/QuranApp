package com.xridwan.alquran.di

import com.xridwan.alquran.data.preference.HistoryPreference
import com.xridwan.alquran.presenter.detail.DetailViewModel
import com.xridwan.alquran.presenter.doa.DoaViewModel
import com.xridwan.alquran.presenter.main.SurahViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SurahViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { DoaViewModel(get()) }
}

val prefsModule = module {
    single {
        HistoryPreference(androidApplication())
    }
}