package com.tdi.holyquran.data.api

import com.tdi.holyquran.data.model.MuslimSalatDailyResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MuslimSalatAPI {

    @GET("{city}/daily.json")
    fun getScheduleSalatDaily(
        @Path("city") city: String
    ): Single<MuslimSalatDailyResponse>

    @GET("{city}/daily/{date}.json")
    fun getScheduleSalatDailyByDate(
        @Path("city") city: String,
        @Path("date") date: String
    ): Single<MuslimSalatDailyResponse>
}