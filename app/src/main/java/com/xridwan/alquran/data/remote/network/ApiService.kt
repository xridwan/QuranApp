package com.xridwan.alquran.data.remote.network

import com.xridwan.alquran.data.remote.response.AyatReponse
import com.xridwan.alquran.data.remote.response.Response
import com.xridwan.alquran.data.remote.response.SuratResponse
import com.xridwan.alquran.utils.Config.BASE_URL_DOA
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("data")
    suspend fun getSurah(): List<SuratResponse>

    @GET("surat/{nomor}")
    suspend fun getAyat(
        @Path("nomor") nomor: String
    ): List<AyatReponse>

    @GET(BASE_URL_DOA)
    fun getDoa(): Call<Response>
}