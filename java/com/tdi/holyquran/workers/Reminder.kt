package com.tdi.holyquran.workers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import com.tdi.holyquran.data.model.Azkar
import com.tdi.holyquran.di.AzanReceiver
import dagger.internal.InjectedFieldSignature
import java.lang.reflect.Array.set
import java.util.*
import javax.inject.Inject
import kotlin.random.nextInt

object Reminder {
    const val REMINDER_NOTIFICATION_REQUEST_CODE = 123
    fun startReminder(
        context: Context,
        reminderTime: String = "9:30" ,
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

//        val (hours, min) = reminderTime.split(":").map { it.toInt() }
        val flags =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.FLAG_IMMUTABLE
        else
            PendingIntent.FLAG_UPDATE_CURRENT

        val intent =
            Intent(context.applicationContext, AzanReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(
                    context.applicationContext,
                    reminderId,
                    intent,
                    flags
                )
            }

        val calendar: Calendar = Calendar.getInstance(Locale.ENGLISH).apply {
            set(Calendar.HOUR_OF_DAY, kotlin.random.Random.nextInt(1,12))
            set(Calendar.MINUTE, kotlin.random.Random.nextInt(0,60))
        }

        // If the trigger time you specify is in the past, the alarm triggers immediately.
        // if soo just add one day to required calendar
        // Note: i'm also adding one min cuz if the user clicked on the notification as soon as
        // he receive it it will reschedule the alarm to fire another notification immediately
        if (Calendar.getInstance(Locale.ENGLISH)
                .apply { add(Calendar.MINUTE, 1) }.timeInMillis - calendar.timeInMillis > 0
        ) {
            calendar.add(Calendar.DATE, 1)
        }

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, intent),
            intent
        )
    }

    fun stopReminder(
        context: Context,
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AzanReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                reminderId,
                intent,
                0
            )
        }
        alarmManager.cancel(intent)
    }


}