package com.example.cats

import androidx.recyclerview.widget.DiffUtil
import com.example.vm.share_domain.model.cats.CatsVo

class CatsDiffUtillsCallback: DiffUtil.ItemCallback<CatsVo>() {
    override fun areItemsTheSame(oldItem: CatsVo, newItem: CatsVo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CatsVo, newItem: CatsVo): Boolean {
        return oldItem == newItem
    }
}