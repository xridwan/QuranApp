package com.xridwan.alquran.data.remote

import com.xridwan.alquran.data.remote.network.ApiResponse
import com.xridwan.alquran.data.remote.network.ApiService
import com.xridwan.alquran.data.remote.response.AyatReponse
import com.xridwan.alquran.data.remote.response.DoaResponse
import com.xridwan.alquran.data.remote.response.SuratResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val apiService: ApiService
) {
    suspend fun getSurat(): Flow<ApiResponse<List<SuratResponse>>> {
        return flow {
            try {
                val response = apiService.getSurah()
                if (response.isNotEmpty()) emit(ApiResponse.Success(response))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAyat(id: String): Flow<ApiResponse<List<AyatReponse>>> {
        return flow {
            try {
                val response = apiService.getAyat(id)
                if (response.isNotEmpty()) emit(ApiResponse.Success(response))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDoa(): Flow<ApiResponse<List<DoaResponse>>> {
        return flow {
            try {
                val response = apiService.getDoa()
                if (response.isNotEmpty()) emit(ApiResponse.Success(response))
                else emit(ApiResponse.Empty)
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}