package com.example.predicate.model

import com.example.predicate.model.user.UserSignUpItem

data class Sentence(
    val id: Int,
    val author: UserSignUpItem,
    val dateAdded: String,
    val message: String
)
