package com.mbds.newsletter.repositories

import com.mbds.newsletter.models.ArticlesResponse
import com.mbds.newsletter.models.SourcesResponse
import com.mbds.newsletter.services.NewsApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiRepository {
    private val service: NewsApiService
    init {

        // Add API KEY to Authorization
        val clientBulider = OkHttpClient.Builder()
                .addInterceptor(AuthenticationInterceptor())

        // Enable Logger
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBulider.addInterceptor(interceptor)


        //Build Client
        val client = clientBulider.build()

        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://newsapi.org/v2/")
            client(client)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        service = retrofit.create(NewsApiService::class.java)
    }

    class AuthenticationInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val headers = request
                    .headers
                    .newBuilder()
                    .add("Authorization", "Bearer 91a74b33e53d4850a4c72de214f81173")
                    .build()

            request = request
                    .newBuilder()
                    .headers(headers)
                    .build()
            return chain.proceed(request)
        }

    }

    fun sources(category : String, country:String): SourcesResponse? {
        val response = service.sources(category = category, country = country).execute()
        return response.body()
    }

    fun everything(category : String, sources:String, page:Int=1): ArticlesResponse? {
        val response = service.everything(category = category, sources = sources, page = page).execute()
        return response.body()
    }

    fun headlines(country:String, category : String, page:Int=1): ArticlesResponse? {
        val response = service.headlines(category = category, country = country, page = page).execute()
        return response.body()
    }

}