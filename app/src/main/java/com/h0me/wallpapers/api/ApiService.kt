package com.h0me.wallpapers.api

import com.h0me.wallpapers.BuildConfig
import com.h0me.wallpapers.model.PhotoCollection
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.unsplash.com/"

private val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .run {
        if (BuildConfig.DEBUG) {
            this.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        } else {
            this
        }
    }
    .addInterceptor(Interceptor { chain: Interceptor.Chain ->
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Client-ID " + "_tHJk9yse_q1G3KnrhUaufVOnUtZsIKyv7QXPMAdEJo"
            )
            .build()
        chain.proceed(request)
    })
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ApiService {


    @GET("search/photos")
    suspend fun getCollection(
        @Query("query") query: String,
        @Query("per_page") per_page: Int
    ): Response<PhotoCollection>
}

object Api {
    val service: ApiService by lazy { retrofit.create() }
}
