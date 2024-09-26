package com.example.mobiletest.data.booking

import androidx.room.ColumnInfo

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
data class Origin(
    @ColumnInfo(name = "origin_code")
    val code: String,

    @ColumnInfo(name = "origin_display_name")
    val displayName: String,

    @ColumnInfo(name = "origin_url")
    val url: String
)