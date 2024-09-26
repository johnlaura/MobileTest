package com.example.mobiletest.data.booking

import androidx.room.Entity

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */

data class Booking(
    val shipReference: String,
    val shipToken: String,
    val canIssueTicketChecking: Boolean,
    val expiryTime: String,
    val duration: Int,
    val segments: List<Segment>
)