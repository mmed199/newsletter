package com.mbds.newsletter.database.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mbds.newsletter.database.data.Favorite
import com.mbds.newsletter.models.Article

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavorite(id: Int)
}