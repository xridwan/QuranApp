package com.xridwan.alquran.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "surat")
data class SuratEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "arti") val arti: String?,
    @ColumnInfo(name = "asma") val asma: String?,
    @ColumnInfo(name = "ayat") val ayat: Int?,
    @ColumnInfo(name = "nama") val nama: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "urut") val urut: String?,
    @ColumnInfo(name = "audio") val audio: String?,
    @ColumnInfo(name = "nomor") val nomor: String?,
    @ColumnInfo(name = "rukuk") val rukuk: String?,
    @ColumnInfo(name = "keterangan") val keterangan: String?,
) : Parcelable
