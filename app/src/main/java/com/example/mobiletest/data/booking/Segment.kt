package com.example.mobiletest.data.booking

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/24
 */
@Entity
data class Segment(
    @PrimaryKey val id: Int,

    @Embedded
    val originAndDestinationPair: OriginAndDestinationPair
)