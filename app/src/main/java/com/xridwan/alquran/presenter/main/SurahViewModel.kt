package com.xridwan.alquran.presenter.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xridwan.alquran.data.remote.network.ApiService
import com.xridwan.alquran.data.remote.response.SurahResponse
import com.xridwan.alquran.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurahViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val _surahData = MutableLiveData<Resource<SurahResponse>>()
    val surahData: LiveData<Resource<SurahResponse>> get() = _surahData

    fun getSurah() {
        _surahData.postValue(Resource.loading(null))
        apiService.getSurah().enqueue(object : Callback<SurahResponse> {
            override fun onResponse(call: Call<SurahResponse>, response: Response<SurahResponse>) {
                if (response.isSuccessful) {
                    _surahData.postValue(Resource.success(response.body()))
                } else {
                    _surahData.postValue(Resource.error(null, response.message()))
                }
            }

            override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                _surahData.postValue(Resource.error(null, t.localizedMessage))
            }
        })
    }
}