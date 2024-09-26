package com.example.mobiletest.utilities.singleevent

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
open class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}