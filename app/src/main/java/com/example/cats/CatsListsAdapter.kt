package com.example.cats

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.vm.databinding.ItemDogBinding
import com.example.vm.share_domain.model.dogs.PetsVo

class CatsListsAdapter : ListAdapter<PetsVo, CatsListsAdapter.CatViewHolder>(CatsDiffUtillsCallback()) {
    inner class CatViewHolder(val binding: ItemDogBinding): RecyclerView.ViewHolder(binding.root){

        private fun getDogImage(imageView: ImageView, imageId: String){
            val url = "https://cdn2.thedogapi.com/images/$imageId.jpg"
            imageView.load(url) {
                this.scale(Scale.FILL)
            }
        }

        fun bind(catModel: PetsVo){
            binding.apply {
                dogName.text = catModel.name
                bredFor.text = catModel.bredFor
                origin.text = catModel.origin
                catModel.referenceImageId?.let {
                    getDogImage(imageDog, it)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatViewHolder = CatViewHolder(
        binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}