package com.xridwan.alquran.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Surat(
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
