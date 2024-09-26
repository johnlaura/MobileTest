package com.example.mobiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletest.data.booking.Segment
import com.example.mobiletest.repository.BookingRepository
import com.example.mobiletest.repository.BookingResponseState
import com.example.mobiletest.ui.BookingListAdapter
import com.example.mobiletest.utilities.SharePreferencesUtil
import com.example.mobiletest.viewmodels.BookingViewModel
import com.example.mobiletest.viewmodels.BookingViewModelFactory

class MainActivity : AppCompatActivity() {
    private val tag = MainActivity::class.java.name

    private val bookingViewModel: BookingViewModel by viewModels {
        val repository = BookingRepository()
        this.lifecycle.addObserver(repository)
        BookingViewModelFactory(repository)
    }

    private val bookingListAdapter: BookingListAdapter = BookingListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview: RecyclerView = findViewById(R.id.rv_booking)
        recyclerview.adapter = bookingListAdapter

        bookingViewModel.requestBookingResponseLiveData.observe(this) {
            onRequestData(it.getContentIfNotHandled())
        }
    }

    override fun onResume() {
        super.onResume()
        bookingViewModel.refreshBookingData()
    }

    private fun onRequestData(result: Result<BookingResponseState>?) {
        result?.onSuccess { state ->
            bookingListAdapter.submitList(state.segments)
            when (state) {
                is BookingResponseState.AfterRequest -> {
                }

                is BookingResponseState.ReadCache -> {
                    showToast(state.segments.toString())
                }
            }
        }
        result?.onFailure {
            showToast("Request Failure")
        }
    }

    private fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        Log.d(tag, str)
    }
}