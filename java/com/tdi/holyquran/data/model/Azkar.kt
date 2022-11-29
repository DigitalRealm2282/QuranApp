package com.tdi.holyquran.data.model

import com.google.gson.annotations.SerializedName

//data class Azkar (
//    @SerializedName("category")
//    val category:String,
//    @SerializedName("count")
//    val count: String,
//    @SerializedName("description")
//    val description:String,
//    @SerializedName("reference")
//    val reference:String,
//    @SerializedName("zekr")
//    val zekr:String
//        )

data class Azkar (
    @SerializedName("category"    ) var category    : String? = null,
    @SerializedName("count"       ) var count       : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("reference"   ) var reference   : String? = null,
    @SerializedName("zekr"        ) var zekr        : String? = null)