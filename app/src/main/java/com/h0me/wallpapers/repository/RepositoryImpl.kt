package com.h0me.wallpapers.repository

import com.h0me.wallpapers.api.Api
import com.h0me.wallpapers.dao.PhotoDao
import com.h0me.wallpapers.entity.PhotoEntity
import com.h0me.wallpapers.entity.toEntity
import com.h0me.wallpapers.model.Photo
import com.h0me.wallpapers.model.PhotoCollection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response


class RepositoryImpl(private val dao: PhotoDao) {

    val favouriteData: Flow<List<Photo>> =
        dao.getAllFavourite().map { it.map(PhotoEntity::toDto) }

    val downloadedData: Flow<List<Photo>> =
        dao.getAllDownloaded().map { it.map(PhotoEntity::toDto) }

    private val collectionsData = mutableListOf<PhotoCollection>()

    suspend fun getCollections(queriesList: List<String>): List<PhotoCollection> {
        try {
            for (query in queriesList) {
                val response = Api.service.getCollection(query, 50)
                if (!response.isSuccessful) {
                    throw Exception(response.message())
                } else {
                    val collection = (requireNotNull(response.body()?.results).map {
                        toEntity(
                            it,
                            favourite = false,
                            downloaded = false,
                            query.split(",").first()
                        )
                    })
                    for (photo in collection) {
                        dao.insert(photo)
                    }
                    saveCollection(query, response)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return collectionsData
    }

    suspend fun getCollection(collectionTitle: String): List<Photo> {
        return try {
            dao.getCollection(collectionTitle).map { it.toDto() }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun saveCollection(query: String, response: Response<PhotoCollection>) {
        if (!collectionsData.any {
                it.title == query.split(",").first()
            }) {
            collectionsData.add(requireNotNull(response.body())).also {
                collectionsData.last().title = query.split(",").first()
            }
        }
    }

    suspend fun clickFavourite(photo: Photo) {
        if (!photo.isFavourite) {
            dao.setFavouriteByRegularUrl(photo.url.regular)
        } else {
            dao.unsetFavouriteByRegularUrl(photo.url.regular)
        }
    }

    suspend fun clickDownload(photo: Photo) {
        if (!photo.isDownloaded) {
            dao.setDownloadedByRegularUrl(photo.url.regular)
        }
    }
}
