package com.example.booster.data.datasource.model

data class EditProfileData(
    val status: Int,
    val success: String,
    val message: String,
    val data: EditProfileResponse
)

data class EditProfileResponse(
    val user_name: String,
    val user_university: Int,
    val user_pw: String
)