package com.madeean.madeefy.api

import com.madeean.madeefy.model.ModelAuthLogin
import com.madeean.madeefy.model.ModelRegister
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiRequest {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("password") password: String?,
    ): Call<ModelRegister>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String?,
        @Field("password") password: String?,
    ): Call<ModelAuthLogin>



}