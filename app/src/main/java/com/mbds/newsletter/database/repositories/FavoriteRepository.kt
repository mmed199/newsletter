package com.mbds.newsletter.database.repositories

import androidx.lifecycle.LiveData
import com.mbds.newsletter.database.data.Favorite
import com.mbds.newsletter.database.data.FavoriteDao
import com.mbds.newsletter.models.Article
import com.mbds.newsletter.utils.Converter

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val allFavorites: LiveData<List<Favorite>> = favoriteDao.getAll()


    suspend fun addFavorite(article: Article) {
        favoriteDao.addFavorite(Converter.articleToFavorite(article))
    }

    suspend fun deleteFavorite(id:Int) {
        favoriteDao.deleteFavorite(id)
    }


}