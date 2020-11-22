package com.mbds.newsletter.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mbds.newsletter.database.data.Favorite
import com.mbds.newsletter.models.Article

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY id ASC")
    fun getAll(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(favorite: Favorite)
}