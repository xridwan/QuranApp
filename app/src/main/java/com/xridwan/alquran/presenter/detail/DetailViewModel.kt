package com.xridwan.alquran.presenter.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xridwan.alquran.data.source.remote.network.ApiClient
import com.xridwan.alquran.data.source.remote.response.SurahResponse
import com.xridwan.alquran.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val ayatData = MutableLiveData<Resource<SurahResponse>>()

    fun setDetail(nomor: String) {
        ayatData.postValue(Resource.loading(null))
        ApiClient.getRetrofit().getAyat(nomor).enqueue(object : Callback<SurahResponse> {
            override fun onResponse(call: Call<SurahResponse>, response: Response<SurahResponse>) {
                if (response.isSuccessful) {
                    ayatData.postValue(Resource.success(response.body()))
                } else {
                    ayatData.postValue(Resource.error(null, response.message()))
                }
            }

            override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                ayatData.postValue(Resource.error(null, t.localizedMessage))
            }
        })
    }

    fun getDetail(): LiveData<Resource<SurahResponse>> = ayatData
}