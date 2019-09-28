package com.stephenbain.relisten.common

import com.squareup.moshi.JsonClass

data class Artist(val id: Int, val name: String, val isFeatured: Boolean)

@JsonClass(generateAdapter = true)
data class Show(val id: Int, val displayDate: String, val artist: Artist)
