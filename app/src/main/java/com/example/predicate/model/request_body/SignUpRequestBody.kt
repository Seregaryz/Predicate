package com.example.predicate.model.request_body

import com.google.gson.annotations.SerializedName

data class SignUpRequestBody(

	@SerializedName("confirm")
	val confirm: String,

	@SerializedName("password")
	val password: String,

	@SerializedName("last_name")
	val lastName: String,

	@SerializedName("first_name")
	val firstName: String,

	@SerializedName("email")
	val email: String,

	@SerializedName("username")
	val username: String
)
