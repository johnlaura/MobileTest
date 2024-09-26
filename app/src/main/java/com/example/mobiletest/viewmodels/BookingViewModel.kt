package com.example.mobiletest.viewmodels

import android.util.ArrayMap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletest.repository.BookingRepository
import com.example.mobiletest.repository.BookingRequestState
import com.example.mobiletest.repository.BookingResponseState
import com.example.mobiletest.utilities.singleevent.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
class BookingViewModel(private val repository: BookingRepository) : ViewModel() {
    private val requestBookingFlow = MutableSharedFlow<BookingRequestState>()

    private val requestBookingResponseFlow = requestBookingFlow.map {
        repository.getData(it).first()
    }

    private val _bookingResultFlow = MutableStateFlow<Event<Result<BookingResponseState>>?>(null)
    val bookingResultFlow : Flow<Event<Result<BookingResponseState>>?> = _bookingResultFlow

    init {
        repository.addOnAutoRefreshListener(object : BookingRepository.IOnAutoRefreshListener {
            override fun callback() {
                requestBookingData()
            }
        })

        viewModelScope.launch(Dispatchers.IO) {
            requestBookingResponseFlow.collect {
                _bookingResultFlow.value = it
            }
        }
    }

    fun refreshBookingData() {
        viewModelScope.launch {
            requestBookingFlow.emit(BookingRequestState.ReadCache)
        }
    }

    private fun requestBookingData() {
        viewModelScope.launch {
            val map = ArrayMap<String, String>()
            map["username"] = "username"
            requestBookingFlow.emit(BookingRequestState.Request(map))
        }
    }
}