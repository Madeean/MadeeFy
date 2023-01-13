package com.madeean.madeefy.model

data class ModelLogin(
    val email: String,
    val id:Int,
    val name: String,
    val password: String,
    val admin:Int,
    val token:String
)
