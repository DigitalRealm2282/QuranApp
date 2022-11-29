package com.tdi.holyquran.di.app

import android.app.Application
import com.tdi.holyquran.data.util.GITHUB_URL
import com.tdi.holyquran.data.util.MUSLIM_SALAT_URL
import com.tdi.holyquran.data.util.SharedPreferenceHelper
import com.tdi.holyquran.di.GithubRetrofitQualifier
import com.tdi.holyquran.di.MuslimSalatRetrofitQualifier
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    @MuslimSalatRetrofitQualifier
    fun provideMuslimSalatRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MUSLIM_SALAT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Singleton
    @JvmStatic
    @Provides
    @GithubRetrofitQualifier
    fun provideGithubalatRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Singleton
    @JvmStatic
    @Provides
    fun provideSharedPreferenceHelper(application: Application): SharedPreferenceHelper =
        SharedPreferenceHelper(application)

}