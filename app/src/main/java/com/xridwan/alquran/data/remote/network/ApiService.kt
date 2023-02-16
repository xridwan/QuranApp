package com.xridwan.alquran.data.remote.network

import com.xridwan.alquran.BuildConfig
import com.xridwan.alquran.data.remote.response.AyatReponse
import com.xridwan.alquran.data.remote.response.DoaResponse
import com.xridwan.alquran.data.remote.response.SuratResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("data")
    suspend fun getSurah(): List<SuratResponse>

    @GET("surat/{nomor}")
    suspend fun getAyat(
        @Path("nomor") nomor: String
    ): List<AyatReponse>

    @GET(BuildConfig.BASE_URL_DOA)
    suspend fun getDoa(): List<DoaResponse>
}