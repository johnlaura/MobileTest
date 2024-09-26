package com.example.mobiletest.network.service

import retrofit2.Call
import retrofit2.http.GET

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
interface BookingService {
    @GET("/")
    fun getData(): Call<String>
}