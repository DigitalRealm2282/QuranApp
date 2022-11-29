package com.tdi.holyquran.data.api

import com.tdi.holyquran.data.model.GithubProfileResponse
import io.reactivex.Single
import retrofit2.http.GET

interface GithubAPI {

    @GET("users/TDI2282")
    fun getUserProfile(): Single<GithubProfileResponse>
}