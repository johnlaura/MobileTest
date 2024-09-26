package com.example.mobiletest.repository

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.core.content.edit
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.mobiletest.FILE_NAME
import com.example.mobiletest.MainApplication
import com.example.mobiletest.data.AppDatabase
import com.example.mobiletest.data.booking.Booking
import com.example.mobiletest.data.booking.Segment
import com.example.mobiletest.network.MobileTestNetwork
import com.example.mobiletest.utilities.SharePreferencesUtil
import com.example.mobiletest.utilities.singleevent.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.flow.Flow
import java.lang.RuntimeException

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
class BookingRepository : Repository(), DefaultLifecycleObserver {
    interface IOnAutoRefreshListener {
        fun callback()
    }

    private var onAutoRefreshListener: IOnAutoRefreshListener? = null
    private val refreshDataCode = 1
    private val duration = 5 * 60 * 1000L
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                refreshDataCode -> {
                    onAutoRefreshListener?.callback()
                }
            }
        }
    }

    fun addOnAutoRefreshListener(listener: IOnAutoRefreshListener) {
        onAutoRefreshListener = listener
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        postNextRefreshTime()
    }

    fun getData(state: BookingRequestState): Flow<Event<Result<BookingResponseState>>> {
        return emitEventUseFLow {
            when (state) {
                BookingRequestState.ReadCache -> {
                    val dao = AppDatabase.getBookingDao(MainApplication.instance.applicationContext)
                    Result.success(BookingResponseState.ReadCache(dao.getSegments()))
                }

                is BookingRequestState.Request -> {
                    val data = MobileTestNetwork.getBookingData(state.map)
                    if (data.isSuccessful) {
                        val inputStream =
                            MainApplication.instance.applicationContext.assets.open(FILE_NAME)
                        val jsonReader = JsonReader(inputStream.reader())
                        val bookingType = object : TypeToken<Booking>() {}.type
                        val booking: Booking = Gson().fromJson(jsonReader, bookingType)
                        val segments = booking.segments
                        saveToCache(segments)
                        Result.success(BookingResponseState.AfterRequest(segments))
                    } else {
                        Result.failure(RuntimeException("${data.errorBody()}"))
                    }
                }
            }
        }
    }

    private suspend fun saveToCache(segments: List<Segment>) {
        val dao = AppDatabase.getBookingDao(MainApplication.instance.applicationContext)
        dao.delete()
        dao.insertAll(segments)

        val nextExpiryTime = System.currentTimeMillis() + duration
        SharePreferencesUtil.instance.edit {
            putLong(SharePreferencesUtil.EXPIRY_TIME, nextExpiryTime)
        }
        postNextRefreshTime()
    }

    private fun postNextRefreshTime() {
        val currentTime = System.currentTimeMillis()
        val expiryTime = SharePreferencesUtil.instance.getLong(SharePreferencesUtil.EXPIRY_TIME, 0L)
        val delay = if (currentTime >= expiryTime) {
            0
        } else {
            (expiryTime - currentTime).coerceAtMost(duration)
        }
        handler.removeMessages(refreshDataCode)
        val message = handler.obtainMessage(refreshDataCode)
        handler.sendMessageDelayed(message, delay)
    }
}

sealed class BookingRequestState {
    object ReadCache : BookingRequestState()
    class Request(val map: Map<String, String>) : BookingRequestState()
}

sealed class BookingResponseState(val segments: List<Segment>) {
    class ReadCache(segments: List<Segment>) : BookingResponseState(segments)
    class AfterRequest(segments: List<Segment>) : BookingResponseState(segments)
}