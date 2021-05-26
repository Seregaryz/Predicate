package com.example.predicate.model.user

import com.google.gson.annotations.SerializedName

data class UserAccount(
    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("token")
    val token: String
)