package com.h0me.wallpapers.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.h0me.wallpapers.model.Exif
import com.h0me.wallpapers.model.Photo
import com.h0me.wallpapers.model.PhotoUrl
import com.h0me.wallpapers.model.ProfileImage
import com.h0me.wallpapers.model.Sponsorship
import com.h0me.wallpapers.model.User

@Entity
data class PhotoEntity(
    @PrimaryKey val urlRegular: String,
    val urlFull: String,
    val collection: String,
    val isFavourite: Boolean,
    val isDownloaded: Boolean

) {
    fun toDto() = Photo(
        isFavourite = isFavourite,
        isDownloaded = isDownloaded,
        collection = collection,
        width = "",
        height = "",
        color = "",
        description = "",
        url = PhotoUrl(urlFull, urlRegular),
        user = User("", "", "", ProfileImage("", "", ""), "", ""),
        likes = "",
        sponsorship = Sponsorship(""),
        exif = Exif("", "", "", "", "", "")
    )

    companion object {
        fun fromDto(dto: Photo, favourite: Boolean, downloaded: Boolean, collection: String) =
            PhotoEntity(
                urlRegular = dto.url.regular,
                urlFull = dto.url.full,
                isFavourite = favourite,
                isDownloaded = downloaded,
                collection = collection
            )
    }
}

fun toEntity(
    photo: Photo,
    favourite: Boolean,
    downloaded: Boolean,
    collection: String
): PhotoEntity =
    PhotoEntity.fromDto(photo, favourite, downloaded, collection)

