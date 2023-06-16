package com.h0me.wallpapers.listener

import android.content.Context
import com.h0me.wallpapers.model.Photo
import com.h0me.wallpapers.model.PhotoCollection
import com.h0me.wallpapers.saver.PhotoSaver
import com.h0me.wallpapers.viewmodel.AppViewModel

open class OnInteractionListenerImpl(
    private val context: Context,
    private val viewModel: AppViewModel
) : OnInteractionListener {

    private val photoSaver by lazy { PhotoSaver() }

    override fun onOpenCollection(collection: PhotoCollection) {
    }

    override fun onOpenPhoto(photo: Photo) {
    }

    override fun onClickFavourite(photo: Photo) {
        viewModel.clickFavourite(photo)
    }

    override fun onClickDownload(photo: Photo) {
        photoSaver.downloadPhoto(
            context,
            photo.url.full
        )
        viewModel.clickDownload(photo)
    }
}
