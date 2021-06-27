package com.xridwan.alquran.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.xridwan.alquran.data.source.remote.network.ApiClient
import com.xridwan.alquran.data.source.remote.response.SurahResponse
import com.xridwan.alquran.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val detail = MutableLiveData<Resource<SurahResponse>>()

    fun setDetail(nomor: String) {
        detail.postValue(Resource.loading(null))
        ApiClient.getRetrofit().getAyat(nomor).enqueue(object : Callback<SurahResponse> {
            override fun onResponse(call: Call<SurahResponse>, response: Response<SurahResponse>) {
                if (response.isSuccessful) {
                    detail.postValue(Resource.success(response.body()))
                } else {
                    detail.postValue(Resource.error(null, response.message()))
                }
            }

            override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                detail.postValue(Resource.error(null, t.localizedMessage))
            }
        })
    }

    fun getDetail(): LiveData<Resource<SurahResponse>> = detail
}