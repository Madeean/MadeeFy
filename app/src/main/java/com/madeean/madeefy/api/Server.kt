package com.madeean.madeefy.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object Server {
    private const val baseURL = "http://192.168.100.101:3000/"
    private var retro: Retrofit? = null
    fun konekRetrofit(): Retrofit? {
        if (retro == null) {
            retro = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retro
    }
}
