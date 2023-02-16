package com.xridwan.alquran.data.preference

import android.app.Application
import android.content.Context

class HistoryPreference(application: Application) {

    private val preference = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setHistory(value: Surah) {
        val editor = preference.edit().apply {
            putInt(PREFS_INDEX, value.index ?: 0)
            putString(PREFS_NOMOR, value.nomor)
            putString(PREFS_NAMA, value.nama)
            putString(PREFS_AYAT, value.ayat)
            putString(PREFS_ARTI, value.arti)
            putString(PREFS_TYPE, value.type)
            putInt(PREFS_LAST, value.last ?: 0)
        }
        editor.apply()
    }

    fun getHistory(): Surah {
        val history = Surah()
        history.apply {
            index = preference.getInt(PREFS_INDEX, 0)
            nomor = preference.getString(PREFS_NOMOR, "")
            nama = preference.getString(PREFS_NAMA, "")
            ayat = preference.getString(PREFS_AYAT, "")
            arti = preference.getString(PREFS_ARTI, "")
            type = preference.getString(PREFS_TYPE, "")
            last = preference.getInt(PREFS_LAST, 0)
        }
        return history
    }

    companion object {
        private const val PREFS_NAME = "prefs_history_pref"
        private const val PREFS_INDEX = "prefs_index"
        private const val PREFS_NOMOR = "prefs_nomor"
        private const val PREFS_NAMA = "prefs_nama"
        private const val PREFS_AYAT = "prefs_ayat"
        private const val PREFS_ARTI = "prefs_arti"
        private const val PREFS_TYPE = "prefs_type"
        private const val PREFS_LAST = "prefs_last"
    }
}