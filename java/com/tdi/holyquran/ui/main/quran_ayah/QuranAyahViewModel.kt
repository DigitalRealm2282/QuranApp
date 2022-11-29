package com.tdi.holyquran.ui.main.quran_ayah

import androidx.lifecycle.ViewModel
import com.tdi.holyquran.data.db.AppDatabase
import com.tdi.holyquran.data.util.SharedPreferenceHelper
import javax.inject.Inject

class QuranAyahViewModel @Inject constructor(
    val db: AppDatabase,
    val pref: SharedPreferenceHelper
) : ViewModel() {

    fun getAyahBySura(id: Int) = db.getAyahDao().getAllAyah(id)

    fun getFontSize() = pref.getAyahFontSize()
}