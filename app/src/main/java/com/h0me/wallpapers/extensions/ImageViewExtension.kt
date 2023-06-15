package com.h0me.wallpapers.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.h0me.wallpapers.R

fun ImageView.loadCircle(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.loading_144)
        .error(R.drawable.loading_error_144)
        .timeout(10_000)
        .into(this)
}

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.photo_placeholder)
        .error(R.drawable.photo_placeholder_error)
        .timeout(10_000)
        .into(this)
}
