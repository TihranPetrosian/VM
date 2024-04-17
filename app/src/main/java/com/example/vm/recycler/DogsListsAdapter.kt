package com.example.vm.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.vm.R
import com.example.vm.share_domain.model.dogs.DogVo

class DogsListsAdapter: ListAdapter<DogVo, DogsListsAdapter.DogViewHolder>(DogsDiffUtillsCallback()) {
    inner class DogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private fun getDogImage(imageView: ImageView, imageId: String){
            val url = "https://cdn2.thedogapi.com/images/$imageId.jpg"
            imageView.load(url) {
                this.scale(Scale.FILL)
            }
        }

        fun bind(dogModel: DogVo){

            val dogName = itemView.findViewById<TextView>(R.id.dogName)
            val dogBreed = itemView.findViewById<TextView>(R.id.bredFor)
            val dogOrigin = itemView.findViewById<TextView>(R.id.origin)
            val dogImage = itemView.findViewById<androidx.appcompat.widget.AppCompatImageView>(R.id.imageDog)

            dogName.text = dogModel.name
            dogBreed.text = dogModel.bredFor
            dogOrigin.text = dogModel.origin


            dogModel.referenceImageId?.let {
                getDogImage(dogImage, it)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}