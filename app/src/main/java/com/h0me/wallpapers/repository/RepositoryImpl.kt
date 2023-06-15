package com.h0me.wallpapers.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.h0me.wallpapers.api.Api
import com.h0me.wallpapers.dao.PhotoDao
import com.h0me.wallpapers.db.AppDb
import com.h0me.wallpapers.entity.PhotoEntity
import com.h0me.wallpapers.entity.toEntity
import com.h0me.wallpapers.model.Photo
import com.h0me.wallpapers.model.PhotoCollection
import retrofit2.Response


class RepositoryImpl(private val dao: PhotoDao) {

    val favouriteData: LiveData<List<Photo>> =
        dao.getAllFavourite().map { it.map(PhotoEntity::toDto) }

    val downloadedData: LiveData<List<Photo>> =
    dao.getAllDownloaded().map { it.map(PhotoEntity::toDto) }

    private val collectionsData = mutableListOf<PhotoCollection>()

    suspend fun getCollections(queriesList: List<String>): List<PhotoCollection> {
        try {
            for (query in queriesList) {
                val response = Api.service.getCollection(query, 50)
                if (!response.isSuccessful) {
                    throw Exception(response.message())
                } else {
                    saveCollection(query, response)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return collectionsData
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

    fun getPhotosFromCollection(collectionTitle: String): List<Photo>? {
        val currentCollection = collectionsData.find { it.title == collectionTitle }
        return currentCollection?.results
    }

     suspend fun addFavourite(photo: Photo) {
         if (!photo.isFavourite) {
             dao.insert(photo.toEntity(photo, true, photo.isDownloaded))
         }else{
             dao.removeByRegularUrl(photo.url.regular)
         }
    }

    suspend fun addDownloaded(photo: Photo) {
        if (!photo.isDownloaded) {
            dao.insert(photo.toEntity(photo,photo.isFavourite, true))
        }else{
            dao.removeByRegularUrl(photo.url.regular)
        }
    }

    suspend fun isFavouriteEmpty(): Boolean {
        return when(dao.favouriteCount()){
            0 -> true
            else -> false
        }
    }

    suspend fun isDownloadedEmpty(): Boolean {
        return when(dao.downloadedCount()){
            0 -> true
            else -> false
        }
    }


}
