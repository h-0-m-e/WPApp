package com.h0me.wallpapers.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.h0me.wallpapers.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM PhotoEntity WHERE collection = :collectionTitle")
    suspend fun getCollection(collectionTitle: String): List<PhotoEntity>

    @Query("SELECT * FROM PhotoEntity WHERE isFavourite = 1")
    fun getAllFavourite(): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM PhotoEntity WHERE isDownloaded = 1")
    fun getAllDownloaded(): Flow<List<PhotoEntity>>

    @Query("SELECT COUNT(*) == 0 FROM PhotoEntity WHERE isDownloaded = 1")
    suspend fun downloadedCount(): Int

    @Query("SELECT COUNT(*) == 0 FROM PhotoEntity WHERE isFavourite = 1")
    suspend fun favouriteCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(photo: PhotoEntity)

    @Query("SELECT * FROM PhotoEntity WHERE urlRegular = :regularUrl")
    suspend fun getByRegularUrl(regularUrl: String): PhotoEntity

    @Query(
        """
        UPDATE PhotoEntity SET
        isFavourite = 1
        WHERE urlRegular = :regularUrl
        """
    )
    suspend fun setFavouriteByRegularUrl(regularUrl: String)

    @Query(
        """
        UPDATE PhotoEntity SET
        isFavourite = 0
        WHERE urlRegular = :regularUrl
        """
    )
    suspend fun unsetFavouriteByRegularUrl(regularUrl: String)

    @Query(
        """
        UPDATE PhotoEntity SET
        isDownloaded = 1
        WHERE urlRegular = :regularUrl
        """
    )
    suspend fun setDownloadedByRegularUrl(regularUrl: String)

}
