package com.example.newsapp.data.remote

import com.example.newsapp.model.NewResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/top-headlines")
    fun getBreakingNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Observable<NewResponse>



    @GET("/v2/everything")
    fun searchNews(
        @Query("apiKey")apiKey: String,
        @Query("q")q:String,
        @Query("sortBy")sortBy:String

    ): Observable<NewResponse>


}