package com.mbds.newsletter.models

data class Article (
    val author: String,
    val urlToImage: String,
    val title:String,
    val description:String,
    val url:String,
    val source:Source
)