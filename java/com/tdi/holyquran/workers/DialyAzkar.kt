package com.tdi.holyquran.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bullhead.quranquotes.QuranQuote
import com.tdi.holyquran.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.tdi.holyquran.ui.main.MainActivity
//import dagger.hilt.EntryPoint
//import dagger.hilt.EntryPoints
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
import java.lang.Math.random

class DialyAzkar(private val context: Context, params: WorkerParameters) : Worker(context, params) {

//        @EntryPoint
//        @InstallIn(SingletonComponent::class)
        object Injector {
    fun getAzkar(context: Context): String {
        val randomAya = QuranQuote.getInstance(context)
        return randomAya.randomVerse.arabic
    }

        }

//        private val injector = EntryPoints.get(context, Injector::class.java)

        // get the repository
        private val azkarRepo = Injector.getAzkar(context)

        override fun doWork(): Result {
            CoroutineScope(Dispatchers.IO).launch {

                // get quote of the day from the api
                val result = azkarRepo

                // generate a notification intent
                val notificationIntent = Intent(context, MainActivity::class.java)
                    .setFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    )

                // generate pending intent from the intent
                val pendingIntent: PendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )

//                val pendIntent: PendingIntent = PendingIntent.getActivity(
//                    context,
//                    1,
//                    Intent(this@DialyAzkar.context,MainActivity::class.java),
//                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//                )

                // setup notification builder
                val builder = NotificationCompat.Builder(
                    context,
                    context.getString(R.string.app_name)
                )
                    .setSmallIcon(R.drawable.ic_baseline_mosque_24)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(
                        result
                    )

                    .setStyle(
                        NotificationCompat.BigTextStyle()
                            .bigText(
                                // add author info in the expanded notificaition
                                result
                            )
                    )
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
//                    .setSound(
//                        RingtoneManager.getDefaultUri(R.raw.azan20)
//                    )
                .addAction(
                    R.drawable.ic_baseline_mosque_24,
                    "Open",
                    pendingIntent
                )

                    .setAutoCancel(true)

                // create notification channels for SDK >= SDK 26
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        context.getString(R.string.app_name),
                        context.getString(R.string.app_name),
                        NotificationManager.IMPORTANCE_HIGH
                    )
                )

                // send the notification
                val managerCompat = NotificationManagerCompat.from(context)
                managerCompat.notify(random().toInt(), builder.build())
            }

            // who cares even if the request failed, nvm
            return Result.success()
        }
}