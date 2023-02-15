package com.xridwan.alquran.presenter.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xridwan.alquran.data.remote.network.ApiService
import com.xridwan.alquran.data.remote.response.SurahResponse
import com.xridwan.alquran.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val _ayatData = MutableLiveData<Resource<SurahResponse>>()
    val ayatData: LiveData<Resource<SurahResponse>> get() = _ayatData

    fun getDetail(nomor: String) {
        _ayatData.postValue(Resource.loading(null))
        apiService.getAyat(nomor).enqueue(object : Callback<SurahResponse> {
            override fun onResponse(call: Call<SurahResponse>, response: Response<SurahResponse>) {
                if (response.isSuccessful) {
                    _ayatData.postValue(Resource.success(response.body()))
                } else {
                    _ayatData.postValue(Resource.error(null, response.message()))
                }
            }

            override fun onFailure(call: Call<SurahResponse>, t: Throwable) {
                _ayatData.postValue(Resource.error(null, t.localizedMessage))
            }
        })
    }
}