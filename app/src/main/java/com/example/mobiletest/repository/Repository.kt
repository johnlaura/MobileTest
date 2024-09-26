package com.example.mobiletest.repository

import androidx.lifecycle.liveData
import com.example.mobiletest.utilities.singleevent.Event
import kotlin.coroutines.CoroutineContext

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
open class Repository {
    open fun <T> emitEvent(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(Event(result))
        }
}