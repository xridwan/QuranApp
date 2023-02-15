package com.xridwan.alquran.di

import androidx.viewbinding.BuildConfig
import com.xridwan.alquran.data.source.remote.network.WebService
import com.xridwan.alquran.presenter.detail.DetailViewModel
import com.xridwan.alquran.presenter.doa.DoaViewModel
import com.xridwan.alquran.presenter.main.SurahViewModel
import com.xridwan.alquran.utils.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val interceptor =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebService::class.java)
    }
}

val viewModelModule = module {
    viewModel { SurahViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { DoaViewModel(get()) }
}