package com.h0me.wallpapers.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.h0me.wallpapers.data.CategoriesData
import com.h0me.wallpapers.db.AppDb
import com.h0me.wallpapers.model.Photo
import com.h0me.wallpapers.repository.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RepositoryImpl(
        AppDb.getInstance(application).photoDao()
    )

    private val _dataPhotos = MutableLiveData<List<Photo>>()
    val dataPhotos: LiveData<List<Photo>>
        get() = _dataPhotos

    val dataCategories = MutableLiveData<CategoriesData>()
    val dataFavourite = repository.favouriteData.asLiveData(Dispatchers.Default)
    val dataDownloaded = repository.downloadedData.asLiveData(Dispatchers.Default)

    fun getCollections(queriesList: List<String>) = viewModelScope.launch {
        try {
            dataCategories.value = CategoriesData(repository.getCollections(queriesList))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getCollection(collectionTitle: String) = viewModelScope.launch {
        try {
            _dataPhotos.value = repository.getCollection(collectionTitle)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clickFavourite(photo: Photo) = viewModelScope.launch {
        try {
            repository.clickFavourite(photo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clickDownload(photo: Photo) = viewModelScope.launch {
        try {
            repository.clickDownload(photo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
