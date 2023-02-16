package com.xridwan.alquran.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "doa")
data class DoaEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int?,
    @ColumnInfo("doa") val doa: String?,
    @ColumnInfo("ayat") val ayat: String?,
    @ColumnInfo("latin") val latin: String?,
    @ColumnInfo("artinya") val artinya: String?,
) : Parcelable