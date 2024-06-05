package com.example.dogs

import androidx.recyclerview.widget.DiffUtil
import com.example.vm.share_domain.model.dogs.DogsVo

class DogsDiffUtillsCallback: DiffUtil.ItemCallback<DogsVo>() {
    override fun areItemsTheSame(oldItem: DogsVo, newItem: DogsVo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DogsVo, newItem: DogsVo): Boolean {
        return oldItem == newItem
    }
}