package com.example.mobiletest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mobiletest.DATABASE_NAME
import com.example.mobiletest.data.booking.Segment
import com.example.mobiletest.data.booking.BookingDao

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */

@Database(entities = [Segment::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        fun getBookingDao(context: Context): BookingDao {
            return getInstance(context).bookingDao()
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }

}