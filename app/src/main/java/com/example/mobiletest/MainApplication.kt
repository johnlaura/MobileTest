package com.example.mobiletest

import android.app.Application

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}