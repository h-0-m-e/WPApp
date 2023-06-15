package com.h0me.wallpapers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.h0me.wallpapers.databinding.CardPhotoBinding
import com.h0me.wallpapers.extensions.load
import com.h0me.wallpapers.listener.OnInteractionListener
import com.h0me.wallpapers.model.Photo

class PhotoAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Photo, PhotoViewHolder>(PhotoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = CardPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
    }
}

class PhotoViewHolder(
    private val binding: CardPhotoBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {



    val urlImage = "https://zamanilka.ru/wp-content/uploads/2021/02/blue-wallpaper-phone-1080x1920-20.jpg"

    fun bind(photo: Photo) {
        binding.apply {
            preview.load(photo.url.full)

            root.setOnClickListener {
                onInteractionListener.onOpenPhoto(photo)
            }

        }


    }
}

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.url.regular == newItem.url.regular
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.url.regular == newItem.url.regular
    }
}
