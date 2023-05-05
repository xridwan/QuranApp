package com.xridwan.alquran.data.preference

import android.app.Application
import android.content.Context
import com.xridwan.alquran.utils.Constants.PREFS_ARTI
import com.xridwan.alquran.utils.Constants.PREFS_AYAT
import com.xridwan.alquran.utils.Constants.PREFS_INDEX
import com.xridwan.alquran.utils.Constants.PREFS_LAST
import com.xridwan.alquran.utils.Constants.PREFS_NAMA
import com.xridwan.alquran.utils.Constants.PREFS_NAME
import com.xridwan.alquran.utils.Constants.PREFS_NOMOR
import com.xridwan.alquran.utils.Constants.PREFS_TYPE

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
}