package com.h0me.wallpapers.listener

import com.h0me.wallpapers.model.Photo
import com.h0me.wallpapers.model.PhotoCollection

interface OnInteractionListener {

    fun onOpenCollection(collection: PhotoCollection) {}

    fun onOpenPhoto(photo: Photo) {}
}
