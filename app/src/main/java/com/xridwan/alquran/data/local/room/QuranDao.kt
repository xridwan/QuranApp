package com.xridwan.alquran.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xridwan.alquran.data.local.entity.DoaEntity
import com.xridwan.alquran.data.local.entity.SuratEntity
import com.xridwan.data.source.local.entity.AyatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuranDao {

    @Query("SELECT * FROM surat")
    fun getSurat(): Flow<List<SuratEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurat(suratList: List<SuratEntity>)

    @Query("SELECT * FROM ayat WHERE idSurat = :id")
    fun getAyat(id: Int): Flow<List<AyatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAyat(suratList: List<AyatEntity>)

    @Query("SELECT * FROM doa")
    fun getDoa(): Flow<List<DoaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoa(suratList: List<DoaEntity>)
}