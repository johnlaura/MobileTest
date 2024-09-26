package com.example.mobiletest.data.booking

import androidx.room.ColumnInfo

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
data class Destination(
    @ColumnInfo(name = "destination_code")
    val code: String,

    @ColumnInfo(name = "destination_display_name")
    val displayName: String,

    @ColumnInfo(name = "destination_url")
    val url: String
)