package com.example.booster.data.datasource.model

data class LoginData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: LoginToken
)

data class LoginToken(
    val university_idx: Int,
    val accessToken: String
)