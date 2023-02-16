package com.xridwan.alquran.utils

import com.xridwan.alquran.data.local.entity.SuratEntity
import com.xridwan.alquran.data.remote.response.AyatReponse
import com.xridwan.alquran.data.remote.response.SuratResponse
import com.xridwan.alquran.domain.model.Ayat
import com.xridwan.alquran.domain.model.Surat
import com.xridwan.data.source.local.entity.AyatEntity

object DataMapper {
    fun mapSuratResponseToEntities(input: List<SuratResponse>): List<SuratEntity> {
        val suratList = ArrayList<SuratEntity>()
        input.map {
            val surat = SuratEntity(
                id = it.nomor?.toInt(),
                arti = it.arti,
                asma = it.asma,
                ayat = it.ayat,
                nama = it.nama,
                type = it.type,
                urut = it.urut,
                audio = it.audio,
                nomor = it.nomor,
                rukuk = it.rukuk,
                keterangan = it.keterangan
            )
            suratList.add(surat)
        }
        return suratList
    }

    fun mapSuratEntitiesToDomain(input: List<SuratEntity>): List<Surat> =
        input.map {
            Surat(
                id = it.id,
                arti = it.arti,
                asma = it.asma,
                ayat = it.ayat,
                nama = it.nama,
                type = it.type,
                urut = it.urut,
                audio = it.audio,
                nomor = it.nomor,
                rukuk = it.rukuk,
                keterangan = it.keterangan
            )
        }

    fun mapAyatResponseToEntities(input: List<AyatReponse>, id: Int): List<AyatEntity> {
        val ayatList = ArrayList<AyatEntity>()
        input.map {
            val ayat = AyatEntity(
                idSurat = id,
                ar = it.ar,
                id = it.id,
                tr = it.tr,
                nomor = it.nomor,
            )
            ayatList.add(ayat)
        }
        return ayatList
    }

    fun mapAyatEntitiesToDomain(input: List<AyatEntity>): List<Ayat> =
        input.map {
            Ayat(
                idAyat = it.idAyat,
                idSurat = it.idSurat,
                ar = it.ar,
                id = it.id,
                tr = it.tr,
                nomor = it.nomor
            )
        }
}