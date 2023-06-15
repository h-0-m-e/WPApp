package com.h0me.wallpapers.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.h0me.wallpapers.entity.PhotoEntity

@Dao
interface PhotoDao {

    @Query("SELECT * FROM PhotoEntity WHERE isFavourite = 1")
    fun getAllFavourite(): LiveData<List<PhotoEntity>>

    @Query("SELECT * FROM PhotoEntity WHERE isDownloaded = 1")
    fun getAllDownloaded(): LiveData<List<PhotoEntity>>

    @Query("SELECT COUNT(*) == 0 FROM PhotoEntity WHERE isDownloaded = 1")
    suspend fun downloadedCount(): Int

    @Query("SELECT COUNT(*) == 0 FROM PhotoEntity WHERE isFavourite = 1")
    suspend fun favouriteCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: PhotoEntity)

    @Query("DELETE FROM PhotoEntity WHERE urlRegular =:url")
    suspend fun removeByRegularUrl(url: String)

    @Query("SELECT * FROM PhotoEntity WHERE urlRegular = :url")
    suspend fun getByRegularUrl(url: String): PhotoEntity
}
