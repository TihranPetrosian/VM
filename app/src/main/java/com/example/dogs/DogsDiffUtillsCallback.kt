package com.example.dogs

import androidx.recyclerview.widget.DiffUtil
import com.example.vm.share_domain.model.dogs.PetsVo

class DogsDiffUtillsCallback: DiffUtil.ItemCallback<PetsVo>() {
    override fun areItemsTheSame(oldItem: PetsVo, newItem: PetsVo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PetsVo, newItem: PetsVo): Boolean {
        return oldItem == newItem
    }
}