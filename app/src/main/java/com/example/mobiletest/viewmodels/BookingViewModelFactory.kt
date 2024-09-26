package com.example.mobiletest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobiletest.MainActivity
import com.example.mobiletest.repository.BookingRepository

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
class BookingViewModelFactory(private val repository: BookingRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}