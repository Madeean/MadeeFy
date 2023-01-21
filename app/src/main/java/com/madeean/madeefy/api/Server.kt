package com.madeean.madeefy.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Server {
    private const val baseURL = "https://madeefyback.madee.my.id/"
    private var retro: Retrofit? = null
    fun konekRetrofit(): Retrofit? {
        if (retro == null) {
            val client = OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.MINUTES)
                .writeTimeout(20,TimeUnit.MINUTES)
                .connectTimeout(20,TimeUnit.MINUTES)
                .build()

            retro = Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retro
    }
}
