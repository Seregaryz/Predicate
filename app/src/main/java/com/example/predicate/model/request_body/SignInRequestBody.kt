package com.example.predicate.model.request_body

import com.google.gson.annotations.SerializedName

data class SignInRequestBody(

	@SerializedName("username")
	val username: String,

	@SerializedName("password")
	val password: String
)
