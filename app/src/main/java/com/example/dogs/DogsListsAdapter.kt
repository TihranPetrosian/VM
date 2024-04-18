package com.example.dogs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.vm.databinding.ItemDogBinding
import com.example.vm.share_domain.model.dogs.DogVo

class DogsListsAdapter : ListAdapter<DogVo, DogsListsAdapter.DogViewHolder>(DogsDiffUtillsCallback()) {
    inner class DogViewHolder(val binding: ItemDogBinding): RecyclerView.ViewHolder(binding.root){

        private fun getDogImage(imageView: ImageView, imageId: String){
            val url = "https://cdn2.thedogapi.com/images/$imageId.jpg"
            imageView.load(url) {
                this.scale(Scale.FILL)
            }
        }

        fun bind(dogModel: DogVo){
            binding.apply {
                dogName.text = dogModel.name
                bredFor.text = dogModel.bredFor
                origin.text = dogModel.origin
                dogModel.referenceImageId?.let {
                    getDogImage(imageDog, it)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogViewHolder = DogViewHolder(
        binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}