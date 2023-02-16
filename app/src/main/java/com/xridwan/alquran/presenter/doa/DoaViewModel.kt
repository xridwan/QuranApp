package com.xridwan.alquran.presenter.doa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xridwan.alquran.data.remote.network.ApiService
import com.xridwan.alquran.data.remote.response.Response
import com.xridwan.alquran.data.remote.response.Result
import com.xridwan.alquran.utils.UiState
import retrofit2.Call
import retrofit2.Callback

class DoaViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private val _doaData = MutableLiveData<UiState<Result>>()
    val doaData: LiveData<UiState<Result>> get() = _doaData

    fun getDoa() {
        _doaData.postValue(UiState.loading(null))
        apiService.getDoa().enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful) {
                    val data = response.body()?.result?.data
                    _doaData.postValue(UiState.success(data?.let { Result(it) }))
                } else {
                    _doaData.postValue(UiState.error(null, response.message()))
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                _doaData.postValue(UiState.error(null, t.localizedMessage))
            }
        })
    }
}