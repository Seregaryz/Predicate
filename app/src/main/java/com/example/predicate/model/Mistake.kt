package com.example.predicate.model

data class Mistake(
    val id: Int,
    val sentence: String,
    val type: String,
    val changedByUser: Boolean
)
