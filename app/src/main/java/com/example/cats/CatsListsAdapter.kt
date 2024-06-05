package com.example.cats

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.vm.databinding.ItemCatBinding
import com.example.vm.databinding.ItemDogBinding
import com.example.vm.share_domain.model.cats.CatsVo
import com.example.vm.share_domain.model.dogs.DogsVo

class CatsListsAdapter : ListAdapter<CatsVo, CatsListsAdapter.CatViewHolder>(CatsDiffUtillsCallback()) {
    inner class CatViewHolder(val binding: ItemCatBinding): RecyclerView.ViewHolder(binding.root){

        private fun getCatImage(imageView: ImageView, imageId: String){
            val url = "https://cdn2.thecatapi.com/images/$imageId.jpg"
            imageView.load(url) {
                this.scale(Scale.FILL)
            }
        }

        fun bind(catModel: CatsVo){
            binding.apply {
                catName.text = catModel.name
                catDescription.text = catModel.description
                catTemperament.text = catModel.temperament

                catModel.referenceImageId?.let {
                    getCatImage(imageCat, it)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatViewHolder = CatViewHolder(
        binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}