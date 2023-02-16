package com.xridwan.alquran.data.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoaResponse(
    val id: String,
    val doa: String,
    val ayat: String,
    val latin: String,
    val artinya: String,
) : Parcelable
