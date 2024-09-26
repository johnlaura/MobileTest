package com.example.mobiletest.network

import com.example.mobiletest.network.service.BookingService
import retrofit2.await
import retrofit2.awaitResponse

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
object MobileTestNetwork {

    private val bookingService = ServiceCreator.create<BookingService>()

    suspend fun getBookingData(map: Map<String, String>) =
        bookingService.getData().awaitResponse()

}