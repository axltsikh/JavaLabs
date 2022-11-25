package com.example.twelvelab.NewsClasses

import com.example.twelvelab.NewsClasses.Rss
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("https://events.devby.io/rss/")
    fun callRss(): Call<Rss>
}