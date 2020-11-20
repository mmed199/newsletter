package com.mbds.newsletter.services

import com.mbds.newsletter.models.ArticlesResponse
import com.mbds.newsletter.models.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("/v2/sources")
    fun sources(@Query("category") category : String, @Query("country") country : String): Call<SourcesResponse>

    @GET("/v2/everything")
    fun everything(@Query("q") category : String, @Query("sources") sources : String): Call<ArticlesResponse>

    @GET("/v2/top-headlines")
    fun headlines(@Query("country") country : String, @Query("q") category: String): Call<ArticlesResponse>
}