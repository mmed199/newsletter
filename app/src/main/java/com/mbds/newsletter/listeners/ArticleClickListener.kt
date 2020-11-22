package com.mbds.newsletter.listeners

import com.mbds.newsletter.models.Article

interface ArticleClickListener {
    fun onCustomerClick(article: Article, favoriteId:Int?)
}