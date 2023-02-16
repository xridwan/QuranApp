package com.xridwan.alquran.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SuratResponse(
    val id: Int?,
    val arti: String?,
    val asma: String?,
    val ayat: Int?,
    val nama: String?,
    val type: String?,
    val urut: String?,
    val audio: String?,
    val nomor: String?,
    val rukuk: String?,
    val keterangan: String?,
) : Parcelable
