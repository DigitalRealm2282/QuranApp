package com.tdi.holyquran.di.ui.main

import androidx.lifecycle.ViewModel
import com.tdi.holyquran.di.viewmodel.ViewModelKey
import com.tdi.holyquran.ui.main.about.AboutViewModel
import com.tdi.holyquran.ui.main.home.HomeViewModel
import com.tdi.holyquran.ui.main.quran_ayah.QuranAyahViewModel
import com.tdi.holyquran.ui.main.quran_sura.QuranSuraViewModel
import com.tdi.holyquran.ui.main.schedule_prayer.SchedulePrayerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuranSuraViewModel::class)
    abstract fun bindQuranSuraViewModel(quranViewModel: QuranSuraViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuranAyahViewModel::class)
    abstract fun bindQuranAyahViewModel(quranAyahViewModel: QuranAyahViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindAboutViewModel(aboutViewModel: AboutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SchedulePrayerViewModel::class)
    abstract fun bindSchedulePrayerViewModel(schedulePrayerViewModel: SchedulePrayerViewModel): ViewModel

}