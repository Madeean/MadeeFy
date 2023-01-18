package com.madeean.madeefy.api

import com.madeean.madeefy.model.ModelAuthLogin
import com.madeean.madeefy.model.ModelListMusik
import com.madeean.madeefy.model.ModelRegister
import retrofit2.Call
import retrofit2.http.*

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

    @GET("musik")
    fun getAllMusik(
        @Header ("Authorization") Authorization:String?
    ):Call<ModelListMusik>

    @GET("musik/sendiri")
    fun getMusikSendiri(
        @Header ("Authorization") Authorization:String?
    ):Call<ModelListMusik>


}