package com.mbds.newsletter.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mbds.newsletter.database.data.Favorite
import com.mbds.newsletter.database.repositories.FavoriteRepository
import com.mbds.newsletter.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    val allFavorites: LiveData<List<Favorite>>
    private val repository: FavoriteRepository
    init {
        val favoriteDao = AppDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
        allFavorites = repository.allFavorites
    }

    fun addFavorite(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(article)
        }
    }

    fun deleteFavorite(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(id)
        }
    }
}