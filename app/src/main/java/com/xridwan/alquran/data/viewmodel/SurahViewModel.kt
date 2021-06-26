package com.xridwan.alquran.data.viewmodel

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
    private val surahs = MutableLiveData<Resource<SurahResponse>>()

    fun setSurah() {
        surahs.postValue(Resource.loading(null))
        ApiClient.getRetrofit().getSurah().enqueue(object : Callback<SurahResponse> {
            override fun onResponse(call: Call<SurahResponse>, response: Response<SurahResponse>) {
                if (response.isSuccessful) {
                    surahs.postValue(Resource.success(response.body()))
                } else {
                    surahs.postValue(Resource.error(null, response.message()))
                }
            }

            override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                surahs.postValue(Resource.error(null, t.localizedMessage))
            }
        })
    }

    fun getSurah(): LiveData<Resource<SurahResponse>> = surahs
}