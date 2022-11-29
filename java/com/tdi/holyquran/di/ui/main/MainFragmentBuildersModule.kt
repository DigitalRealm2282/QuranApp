package com.tdi.holyquran.di.ui.main

import com.tdi.holyquran.ui.main.about.AboutFragment
import com.tdi.holyquran.ui.main.home.HomeFragment
import com.tdi.holyquran.ui.main.quran_ayah.QuranAyahFragment
import com.tdi.holyquran.ui.main.quran_sura.QuranSuraFragment
import com.tdi.holyquran.ui.main.schedule_prayer.SchedulePrayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeQuranSuraFragment(): QuranSuraFragment

    @ContributesAndroidInjector()
    abstract fun contributeQuranAyahragment(): QuranAyahFragment

    @ContributesAndroidInjector()
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector()
    abstract fun contributeAboutFragment(): AboutFragment

    @ContributesAndroidInjector()
    abstract fun contributeSchedulePrayerFragment(): SchedulePrayerFragment


}