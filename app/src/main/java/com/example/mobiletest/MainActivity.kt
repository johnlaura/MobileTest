package com.example.mobiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletest.repository.BookingRepository
import com.example.mobiletest.repository.BookingResponseState
import com.example.mobiletest.ui.BookingListAdapter
import com.example.mobiletest.viewmodels.BookingViewModel
import com.example.mobiletest.viewmodels.BookingViewModelFactory
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val tag = MainActivity::class.java.name
    private val repository = BookingRepository()

    private val bookingViewModel: BookingViewModel by viewModels {
        this.lifecycle.addObserver(repository)
        BookingViewModelFactory(repository)
    }

    private val bookingListAdapter: BookingListAdapter = BookingListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview: RecyclerView = findViewById(R.id.rv_booking)
        recyclerview.adapter = bookingListAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookingViewModel.bookingResultFlow.filterNotNull().collect {
                    onRequestData(it.getContentIfNotHandled())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bookingViewModel.refreshBookingData()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.lifecycle.removeObserver(repository)
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
            showToast(it.message ?: "")
        }
    }

    private fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        Log.d(tag, str)
    }
}