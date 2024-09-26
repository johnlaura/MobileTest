package com.example.mobiletest.data.booking

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */

@Dao
interface BookingDao {
    @Query("SELECT * FROM segment")
    fun getSegments(): List<Segment>

    @Query("DELETE FROM segment")
    fun delete()

    @Insert
    fun insertAll(segments: List<Segment>)
}