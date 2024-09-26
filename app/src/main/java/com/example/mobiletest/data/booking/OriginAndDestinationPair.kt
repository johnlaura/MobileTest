package com.example.mobiletest.data.booking

import androidx.room.ColumnInfo
import androidx.room.Embedded

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/24
 */
data class OriginAndDestinationPair(
    @Embedded val destination: Destination,

    @ColumnInfo(name = "destination_city")
    val destinationCity: String,

    @Embedded val origin: Origin,

    @ColumnInfo(name = "origin_city")
    val originCity: String
)