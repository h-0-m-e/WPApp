package com.h0me.wallpapers.saver

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class PhotoSaver {


    fun downloadPhoto(context: Context, photoUrl: String) {
        Glide.with(context)
            .asBitmap()
            .load(photoUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    val filename = "WallpapersImage_${System.currentTimeMillis()}.jpg"

                    var fos: OutputStream? = null

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        context.contentResolver?.also { resolver ->

                            val contentValues = ContentValues().apply {

                                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                                put(
                                    MediaStore.MediaColumns.RELATIVE_PATH,
                                    Environment.DIRECTORY_PICTURES
                                )
                            }

                            val imageUri: Uri? =
                                resolver.insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    contentValues
                                )

                            fos = imageUri?.let { resolver.openOutputStream(it) }
                        }
                    } else {
                        val imagesDir =
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        val image = File(imagesDir, filename)
                        fos = FileOutputStream(image)
                    }

                    fos?.use {
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    }
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {}

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}
