package com.mbds.newsletter.models

import androidx.room.Entity

data class Article (
    val author: String,
    val urlToImage: String,
    val title:String,
    val description:String = "",
    val url:String,
    val source:Source?,
    val content:String = "",
    val publishedAt:String
)