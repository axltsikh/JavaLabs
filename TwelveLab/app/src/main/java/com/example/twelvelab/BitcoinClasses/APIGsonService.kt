package com.example.twelvelab.BitcoinClasses

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface APIGsonService {
    @GET("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD,JPY,EUR")
    fun getValues(): Call<BitcoinClass>
}