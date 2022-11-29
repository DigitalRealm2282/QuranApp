package com.tdi.holyquran.di.app

import com.tdi.holyquran.di.MainScope
import com.tdi.holyquran.di.ui.main.MainFragmentBuildersModule
import com.tdi.holyquran.di.ui.main.MainModule
import com.tdi.holyquran.di.ui.main.MainViewModelModule
import com.tdi.holyquran.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuildersModule::class,
            MainModule::class,
            MainViewModelModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

}