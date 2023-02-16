package com.xridwan.alquran.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doa(
    val id: Int?,
    val doa: String?,
    val ayat: String?,
    val latin: String?,
    val artinya: String?,
) : Parcelable
