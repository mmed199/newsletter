package com.mbds.newsletter.models

import java.io.Serializable

class ArticlesResponse(val status: String, val articles: Array<Article>) : Serializable {

}