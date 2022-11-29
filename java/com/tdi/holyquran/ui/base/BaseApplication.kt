package com.tdi.holyquran.ui.base

import androidx.work.*
import com.tdi.holyquran.di.app.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import java.util.concurrent.TimeUnit

class BaseApplication : DaggerApplication() {

//    override fun onCreate() {
//        super.onCreate()
//
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
//            .build()
//        // setup work request for daily motivational quote
//        val workRequest = PeriodicWorkRequest
//            .Builder(DialyAzkar::class.java, 2, TimeUnit.DAYS)
//            .setConstraints(constraints)
//            .build()
//
//        // enqueue unique periodic work so it doesn't get repeated
//        WorkManager
//            .getInstance(this)
//            .enqueueUniquePeriodicWork(
//                getString(
//                    com.tdi.holyquran.R.string.app_name
//                ),
//                ExistingPeriodicWorkPolicy.KEEP,
//                workRequest
//            )
//    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .application(this)
            .build()

}
