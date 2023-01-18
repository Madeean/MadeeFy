package com.madeean.madeefy.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object Server {
    private const val baseURL = "https://madeefyback.madee.my.id/"
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
