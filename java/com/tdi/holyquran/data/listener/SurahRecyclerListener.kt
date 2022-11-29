package com.tdi.holyquran.data.listener

import android.view.View
import com.tdi.holyquran.data.db.entities.Sura

interface SurahRecyclerListener {

    fun onClick(view: View, sura: Sura)
}