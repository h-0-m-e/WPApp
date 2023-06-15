package com.h0me.wallpapers.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.h0me.wallpapers.data.CategoriesData
import com.h0me.wallpapers.data.PhotosData
import com.h0me.wallpapers.db.AppDb
import com.h0me.wallpapers.model.Photo
import com.h0me.wallpapers.repository.RepositoryImpl
import kotlinx.coroutines.launch

class AppViewModel(application: Application): AndroidViewModel(application) {
    private val repository = RepositoryImpl(
        AppDb.getInstance(application).photoDao()
    )

    val dataPhotos = MutableLiveData<PhotosData>()
    val dataCategories = MutableLiveData<CategoriesData>()
    val dataFavourite = repository.favouriteData
    val dataDownloaded = repository.downloadedData

    fun getCollections(queriesList: List<String>) = viewModelScope.launch {
        try {
            dataCategories.value = CategoriesData(repository.getCollections(queriesList))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getPhotos(collectionTitle: String) {
        try {
            dataPhotos.value = PhotosData(requireNotNull(repository.getPhotosFromCollection(collectionTitle)))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

//    fun getFavourite(collectionTitle: String) {
//        try {
//            dataFavourite. = repository.favouriteData
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }

    fun addFavourite(photo: Photo) = viewModelScope.launch {
        try {
            repository.addFavourite(photo)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

//    fun getDownloaded(collectionTitle: String) {
//        try {
//            dataPhotos.value = PhotosData(requireNotNull(repository.getPhotosFromCollection(collectionTitle)))
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }

    fun addDownloaded(photo: Photo) = viewModelScope.launch {
        try {
            repository.addDownloaded(photo)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
