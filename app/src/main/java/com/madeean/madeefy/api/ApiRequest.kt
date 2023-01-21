package com.madeean.madeefy.api

import com.madeean.madeefy.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("musik/tambah-musik")
    fun tambahMusik(
        @Header("Authorization") token: String?,
        @Part file: MultipartBody.Part,
        @Part("judul") judul: RequestBody?,
        @Part("deskripsi") deskripsi: RequestBody?,
        @Part("publik") publik: RequestBody?
    ): Call<ModelDataMusik?>?



    @POST("logout")
    fun logout(
        @Header ("Authorization") Authorization:String

    ):Call<ModelLogout>


}