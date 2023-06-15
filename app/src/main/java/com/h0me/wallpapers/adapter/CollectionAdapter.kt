package com.h0me.wallpapers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.h0me.wallpapers.databinding.CardCollectionBinding
import com.h0me.wallpapers.extensions.loadCircle
import com.h0me.wallpapers.listener.OnInteractionListener
import com.h0me.wallpapers.model.PhotoCollection

class CollectionAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<PhotoCollection, CollectionViewHolder>(CollectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding = CardCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val collection = getItem(position)
        holder.bind(collection)
    }
}

class CollectionViewHolder(
    private val binding: CardCollectionBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {



    val urlImage = "https://zamanilka.ru/wp-content/uploads/2021/02/blue-wallpaper-phone-1080x1920-20.jpg"

    fun bind(collection: PhotoCollection) {
        binding.apply {
            title.text = collection.title
            count.text = collection.total
            preview.loadCircle(collection.results.first().url.regular)

            root.setOnClickListener {
                onInteractionListener.onOpenCollection(collection)
            }
        }
    }
}

class CollectionDiffCallback : DiffUtil.ItemCallback<PhotoCollection>() {
    override fun areItemsTheSame(oldItem: PhotoCollection, newItem: PhotoCollection): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: PhotoCollection, newItem: PhotoCollection): Boolean {
        return oldItem.total == newItem.total
    }
}
