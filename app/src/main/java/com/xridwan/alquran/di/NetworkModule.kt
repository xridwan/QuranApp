package com.xridwan.alquran.di

import com.xridwan.alquran.BuildConfig
import com.xridwan.alquran.data.QuranRepositoryImpl
import com.xridwan.alquran.data.local.LocalDataSource
import com.xridwan.alquran.data.remote.RemoteDataSource
import com.xridwan.alquran.data.remote.network.ApiService
import com.xridwan.alquran.domain.repository.QuranRepository
import com.xridwan.alquran.domain.usecase.QuranUseCase
import com.xridwan.alquran.domain.usecase.QuranUseCaseImpl
import com.xridwan.alquran.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
            .connectTimeout(Constants.CONN_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

val dataSourceModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
}

val repositoryModule = module {
    single<QuranRepository> {
        QuranRepositoryImpl(get(), get())
    }
}

val useCaseModule = module {
    single<QuranUseCase> {
        QuranUseCaseImpl(get())
    }
}