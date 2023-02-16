package com.xridwan.alquran.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ayat(
    val idAyat: Int?,
    val idSurat: Int?,
    val ar: String?,
    val id: String?,
    val tr: String?,
    val nomor: String?,
) : Parcelable
