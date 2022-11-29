package com.tdi.holyquran.ui.main.quran_sura

import androidx.lifecycle.ViewModel
import com.tdi.holyquran.data.db.AppDatabase
import javax.inject.Inject

class QuranSuraViewModel @Inject constructor(
    val db: AppDatabase
) : ViewModel() {

    fun getAllSura(search: String) = db.getAyahDao().getAllSura(search)

}