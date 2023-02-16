package com.xridwan.alquran.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AyatReponse(
    val idAyat: Int?,
    val idSurat: Int?,
    val ar: String?,
    val id: String?,
    val tr: String?,
    val nomor: String?,
) : Parcelable
