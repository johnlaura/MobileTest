package com.example.mobiletest.viewmodels

import android.util.ArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.mobiletest.repository.BookingRepository
import com.example.mobiletest.repository.BookingRequestState
import com.example.mobiletest.repository.BookingResponseState
import com.example.mobiletest.utilities.singleevent.Event

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
class BookingViewModel(private val repository: BookingRepository) : ViewModel() {

    private val requestBookingLiveData = MutableLiveData<BookingRequestState>()

    val requestBookingResponseLiveData: LiveData<Event<Result<BookingResponseState>>> =
        requestBookingLiveData.switchMap {
            repository.getData(it)
        }

    init {
        repository.addOnAutoRefreshListener(object : BookingRepository.IOnAutoRefreshListener {
            override fun callback() {
                requestBookingData()
            }
        })
    }

    fun refreshBookingData() {
        requestBookingLiveData.value = BookingRequestState.ReadCache
    }

    private fun requestBookingData() {
        val map = ArrayMap<String, String>()
        map["username"] = "username"
        requestBookingLiveData.value = BookingRequestState.Request(map)
    }
}