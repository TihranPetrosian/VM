package com.example.vm.recycler

import androidx.recyclerview.widget.DiffUtil

class DogsDiffUtillsCallback: DiffUtil.ItemCallback<DogsItem>() {
    override fun areItemsTheSame(oldItem: DogsItem, newItem: DogsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DogsItem, newItem: DogsItem): Boolean {
        return oldItem == newItem
    }
}