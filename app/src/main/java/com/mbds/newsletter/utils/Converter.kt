package com.mbds.newsletter.utils

import com.mbds.newsletter.database.data.Favorite
import com.mbds.newsletter.models.Article
import com.mbds.newsletter.models.Source

class Converter {
    companion object {
        fun favoritesToArticles(favorites: Array<Favorite>): MutableList<Article> {
            var toReturn:MutableList<Article> = mutableListOf()

            favorites.forEachIndexed {
                index, favorite ->
                toReturn.add(index, Article(
                        author = favorite.author?:"",
                        urlToImage = favorite.urlToImage,
                        description = favorite.description,
                        title = favorite.title,
                        source = Source(id = "", name = favorite.source?:"", country = ""),
                        url = favorite.url,
                        content = favorite.content,
                        publishedAt = favorite.publishedAt
                    ))
            }

            return toReturn
        }
        fun articleToFavorite(article: Article) : Favorite {
            return  Favorite(id = 0,
                url = article.url,
                author = article.author?:"",
                title = article.title,
                description = article.description,
                source = article.source?.name?:"",
                urlToImage = article.urlToImage,
                content = article.content,
                publishedAt = article.publishedAt
                )
        }

    }
}