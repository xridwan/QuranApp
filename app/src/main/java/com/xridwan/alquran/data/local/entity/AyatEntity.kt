package com.xridwan.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ayat")
data class AyatEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idAyat") val idAyat: Int = 0,
    @ColumnInfo(name = "idSurat") val idSurat: Int?,
    @ColumnInfo(name = "ar") val ar: String?,
    @ColumnInfo(name = "id") val id: String?,
    @ColumnInfo(name = "tr") val tr: String?,
    @ColumnInfo(name = "nomor") val nomor: String?,
) : Parcelable
