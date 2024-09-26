package com.example.mobiletest.utilities

import android.content.Context
import android.content.SharedPreferences
import com.example.mobiletest.MainApplication

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
object SharePreferencesUtil {
    const val EXPIRY_TIME = "expiry_time"

    val instance: SharedPreferences = MainApplication.instance.applicationContext.getSharedPreferences(
        "mobile_test",
        Context.MODE_PRIVATE
    )
}