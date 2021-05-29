package com.example.predicate.model.request_body

import com.google.gson.annotations.SerializedName

data class ArgumentRequestBody(

    @SerializedName("message")
    val message: String
)