package com.stephenbain.relisten.common

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(val id: Int, val name: String, val isFeatured: Boolean) : Parcelable

@JsonClass(generateAdapter = true)
data class Show(val id: Int, @Json(name = "display_date") val displayDate: String, val artist: Artist)
