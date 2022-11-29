package com.tdi.holyquran.data.model

import com.google.gson.annotations.SerializedName

data class MuslimSalatDailyResponse(
    val address: String,
    val city: String,
    val country: String,
    val country_code: String,
    @SerializedName("items")
    val scheduleOfPrays: List<ScheduleOfPray>,
    val postal_code: String,
    val state: String,
    val status_code: Int,
    @SerializedName("today_weather")
    val todayWeather: TodayWeather
)