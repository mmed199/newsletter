package com.mbds.newsletter.database.repositories

import androidx.lifecycle.LiveData
import com.mbds.newsletter.database.data.Favorite
import com.mbds.newsletter.database.data.FavoriteDao
import com.mbds.newsletter.models.Article

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    val allFavorites: LiveData<List<Favorite>> = favoriteDao.getAll()

    suspend fun addFavorite(article: Article) {
        favoriteDao.addFavorite(convertToFavorite(article))
    }

    suspend fun deleteFavorite(id:Int) {
        favoriteDao.deleteFavorite(id)
    }

    private fun convertToFavorite(article: Article) : Favorite {
        return  Favorite(id = 0, url = article.url, author = article.author?:"", title = article.title, description = article.description, source = article.source?.name?:"", urlToImage = article.urlToImage)
    }
}