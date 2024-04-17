package com.example.vm.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.vm.share_domain.model.dogs.DogVo

class DogsDiffUtillsCallback: DiffUtil.ItemCallback<DogVo>() {
    override fun areItemsTheSame(oldItem: DogVo, newItem: DogVo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DogVo, newItem: DogVo): Boolean {
        return oldItem == newItem
    }
}