package com.h0me.wallpapers.listener

import android.content.Context
import androidx.lifecycle.ViewModel
import com.h0me.wallpapers.model.Photo
import com.h0me.wallpapers.model.PhotoCollection

open class OnInteractionListenerImpl(
    private val context: Context,
    private val viewModel: ViewModel
):OnInteractionListener

{
    override fun onOpenCollection(collection: PhotoCollection) {
    }

    override fun onOpenPhoto(photo: Photo) {
    }
}
