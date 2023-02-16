package com.xridwan.alquran.data

import com.xridwan.alquran.data.local.LocalDataSource
import com.xridwan.alquran.data.remote.RemoteDataSource
import com.xridwan.alquran.data.remote.network.ApiResponse
import com.xridwan.alquran.data.remote.response.AyatReponse
import com.xridwan.alquran.data.remote.response.DoaResponse
import com.xridwan.alquran.data.remote.response.SuratResponse
import com.xridwan.alquran.domain.Resource
import com.xridwan.alquran.domain.model.Ayat
import com.xridwan.alquran.domain.model.Doa
import com.xridwan.alquran.domain.model.Surat
import com.xridwan.alquran.domain.repository.QuranRepository
import com.xridwan.alquran.utils.DataMapper
import com.xridwan.alquran.utils.DataMapper.mapAyatEntitiesToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuranRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : QuranRepository {
    override fun getSurat(): Flow<Resource<List<Surat>>> =
        object : NetworkBoundResource<List<Surat>, List<SuratResponse>>() {
            override fun loadFromDB(): Flow<List<Surat>> {
                return localDataSource.getSurat().map {
                    DataMapper.mapSuratEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Surat>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<SuratResponse>>> {
                return remoteDataSource.getSurat()
            }

            override suspend fun saveCallResult(data: List<SuratResponse>) {
                val suratList = DataMapper.mapSuratResponseToEntities(data)
                localDataSource.insertSurat(suratList)
            }
        }.asFlow()

    override fun getAyat(id: Int): Flow<Resource<List<Ayat>>> =
        object : NetworkBoundResource<List<Ayat>, List<AyatReponse>>() {
            override fun loadFromDB(): Flow<List<Ayat>> {
                return localDataSource.getAyat(id).map {
                    mapAyatEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Ayat>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<AyatReponse>>> {
                return remoteDataSource.getAyat(id.toString())
            }

            override suspend fun saveCallResult(data: List<AyatReponse>) {
                val ayatList = DataMapper.mapAyatResponseToEntities(data, id)
                localDataSource.insertAyat(ayatList)
            }
        }.asFlow()

    override fun getDoa(): Flow<Resource<List<Doa>>> =
        object : NetworkBoundResource<List<Doa>, List<DoaResponse>>() {
            override fun loadFromDB(): Flow<List<Doa>> {
                return localDataSource.getDoa().map {
                    DataMapper.mapDoaEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Doa>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<DoaResponse>>> {
                return remoteDataSource.getDoa()
            }

            override suspend fun saveCallResult(data: List<DoaResponse>) {
                val suratList = DataMapper.mapDoaResponseToEntities(data)
                localDataSource.insertDoa(suratList)
            }
        }.asFlow()
}