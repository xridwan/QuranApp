package com.xridwan.alquran.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xridwan.alquran.data.source.remote.response.Response
import com.xridwan.alquran.data.source.remote.response.Result
import com.xridwan.alquran.data.source.remote.network.ApiClient
import com.xridwan.alquran.utils.Resource
import retrofit2.Call
import retrofit2.Callback

class DoaViewModel : ViewModel() {
    private val doa = MutableLiveData<Resource<Result>>()

    fun setDoa() {
        doa.postValue(Resource.loading(null))
        ApiClient.getRetrofit().getDoa().enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    val data = response.body()?.result?.data
                    doa.postValue(Resource.success(data?.let { Result(it) }))
                } else {
                    doa.postValue(Resource.error(null, response.message()))
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                doa.postValue(Resource.error(null, t.localizedMessage))
            }
        })
    }

    fun getDoa(): LiveData<Resource<Result>> {
        return doa
    }
}