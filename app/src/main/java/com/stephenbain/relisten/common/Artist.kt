package com.stephenbain.relisten.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Artist(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "is_featured") val isFeatured: Boolean
)
