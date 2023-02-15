package com.xridwan.alquran.presenter.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xridwan.alquran.data.source.remote.network.ApiClient
import com.xridwan.alquran.data.source.remote.response.SurahResponse
import com.xridwan.alquran.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurahViewModel : ViewModel() {
    private val surahData = MutableLiveData<Resource<SurahResponse>>()

    fun setSurah() {
        surahData.postValue(Resource.loading(null))
        ApiClient.getRetrofit().getSurah().enqueue(object : Callback<SurahResponse> {
            override fun onResponse(call: Call<SurahResponse>, response: Response<SurahResponse>) {
                if (response.isSuccessful) {
                    surahData.postValue(Resource.success(response.body()))
                } else {
                    surahData.postValue(Resource.error(null, response.message()))
                }
            }

            override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                surahData.postValue(Resource.error(null, t.localizedMessage))
            }
        })
    }

    fun getSurah(): LiveData<Resource<SurahResponse>> = surahData
}