package com.xridwan.alquran.data.source.remote.network

import com.xridwan.alquran.data.source.remote.response.Response
import com.xridwan.alquran.data.source.remote.response.SurahResponse
import com.xridwan.alquran.utils.Config.BASE_URL_DOA
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {
    @GET("data")
    fun getSurah(): Call<SurahResponse>

    @GET("surat/{nomor}")
    fun getAyat(
        @Path("nomor") nomor: String
    ): Call<SurahResponse>

    @GET(BASE_URL_DOA)
    fun getDoa(): Call<Response>
}