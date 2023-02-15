package com.xridwan.alquran.data.local.preference

import android.content.Context
import com.xridwan.alquran.data.local.entity.Surah

internal class HistoryPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "history_pref"
        private const val INDEX = "index"
        private const val NOMOR = "nomor"
        private const val NAMA = "nama"
        private const val AYAT = "ayat"
        private const val ARTI = "arti"
        private const val TYPE = "type"
        private const val LAST = "last"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setHistory(value: Surah) {
        val editor = preference.edit().apply {
            putInt(INDEX, value.index ?: 0)
            putString(NOMOR, value.nomor)
            putString(NAMA, value.nama)
            putString(AYAT, value.ayat)
            putString(ARTI, value.arti)
            putString(TYPE, value.type)
            putString(LAST, value.last)
        }
        editor.apply()
    }

    fun getHistory(): Surah {
        val history = Surah()
        history.apply {
            index = preference.getInt(INDEX, 0)
            nomor = preference.getString(NOMOR, "")
            nama = preference.getString(NAMA, "")
            ayat = preference.getString(AYAT, "")
            arti = preference.getString(ARTI, "")
            type = preference.getString(TYPE, "")
            last = preference.getString(LAST, "")
        }
        return history
    }
}