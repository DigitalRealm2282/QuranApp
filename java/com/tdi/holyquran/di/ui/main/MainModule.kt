package com.tdi.holyquran.di.ui.main

import com.tdi.holyquran.data.api.GithubAPI
import com.tdi.holyquran.data.api.MuslimSalatAPI
import com.tdi.holyquran.di.GithubRetrofitQualifier
import com.tdi.holyquran.di.MainScope
import com.tdi.holyquran.di.MuslimSalatRetrofitQualifier
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@Module
object MainModule {

    @MainScope
    @JvmStatic
    @Provides
    fun provideMuslimSalatAPI(@MuslimSalatRetrofitQualifier retrofit: Retrofit): MuslimSalatAPI =
        retrofit.create(MuslimSalatAPI::class.java)

    @MainScope
    @JvmStatic
    @Provides
    fun provideGithubAPI(@GithubRetrofitQualifier retrofit: Retrofit): GithubAPI =
        retrofit.create(GithubAPI::class.java)

    @MainScope
    @JvmStatic
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable =
        CompositeDisposable()
}