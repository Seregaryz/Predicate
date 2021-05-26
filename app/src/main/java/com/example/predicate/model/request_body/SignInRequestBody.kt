package com.example.predicate.model.request_body

import com.google.gson.annotations.SerializedName

data class SignInRequestBody(

	@SerializedName("password")
	val password: String,

	@SerializedName("username")
	val username: String
)
