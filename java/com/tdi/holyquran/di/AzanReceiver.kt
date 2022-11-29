package com.tdi.holyquran.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.bullhead.quranquotes.QuranQuote

import com.tdi.holyquran.R

import com.tdi.holyquran.ui.main.MainActivity
import com.tdi.holyquran.workers.DialyAzkar
import com.tdi.holyquran.workers.Reminder
import java.util.concurrent.TimeUnit

class AzanReceiver : BroadcastReceiver() {

    companion object {
        val ACTION = "com.tdi.holyquran.UPDATE_AZAN"
    }
    override fun onReceive(context: Context, intent: Intent) {

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendReminderNotification(
            applicationContext = context,
            channelId = context.getString(R.string.reminders_notification_channel_id)
        )
        // Remove this line if you don't want to reschedule the reminder

        Reminder.startReminder(context.applicationContext)


        //WorkerApi

      val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()
        // setup work request for daily motivational quote
        val workRequest = PeriodicWorkRequest
            .Builder(DialyAzkar::class.java, 2, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        // enqueue unique periodic work so it doesn't get repeated
        WorkManager
            .getInstance(context)
            .enqueueUniquePeriodicWork(
                context.getString(R.string.app_name),
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )

    }
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
//        val time =

    private fun NotificationManager.sendReminderNotification(
        applicationContext: Context,
        channelId: String,
    ) {

        val randomAya = QuranQuote.getInstance(applicationContext)
        val aya = randomAya.randomVerse.arabic


        val flags =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.FLAG_IMMUTABLE
        else
            PendingIntent.FLAG_UPDATE_CURRENT

        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            contentIntent,
            flags
        )

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(aya)
            .setSmallIcon(R.drawable.ic_baseline_mosque_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(aya)

            )
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val mChannel = NotificationChannel(channelId, applicationContext.getString(R.string.app_name) , NotificationManager.IMPORTANCE_HIGH)

        createNotificationChannel(mChannel)
//        Toast.makeText(applicationContext, "Alarm is supposed to ring", Toast.LENGTH_SHORT).show()


        NotificationCompat.Builder(applicationContext, channelId)
        notify(NOTIFICATION_ID, builder.build())
    }

    val NOTIFICATION_ID = 1
}
