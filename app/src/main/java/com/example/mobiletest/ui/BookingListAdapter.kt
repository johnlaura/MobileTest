package com.example.mobiletest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiletest.data.booking.Segment
import com.example.mobiletest.databinding.ItemSegmentBinding

/**
 * MobileTest
 * Do one thing to change the world
 * create by johnlion at 2024/9/25
 */
class BookingListAdapter :
    ListAdapter<Segment, BookingListAdapter.ViewHolder>(BookingListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSegmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binds(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemSegmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binds(value: Segment) {
            with(binding) {
                segment = value
            }
        }
    }
}

private class BookingListDiffCallback : DiffUtil.ItemCallback<Segment>() {
    override fun areItemsTheSame(oldItem: Segment, newItem: Segment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Segment, newItem: Segment): Boolean {
        return oldItem.id == newItem.id
    }

}