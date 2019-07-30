package com.stephenbain.relisten.common

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "is_featured") val isFeatured: Boolean
)

@Entity(tableName = "shows")
@JsonClass(generateAdapter = true)
data class Show(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "display_date") @Json(name = "display_date") val displayDate: String,
    @Embedded(prefix = "artist_") val artist: Artist
)
