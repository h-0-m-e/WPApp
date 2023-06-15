package com.h0me.wallpapers.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class PhotoCollection(
    @SerializedName("title") var title: String,
    @SerializedName("total") val total: String,
    @SerializedName("pages") val pages: String,
    @SerializedName("results") val results: List<Photo>

)
