package com.example.predicate.model.user

data class UserSignUpItem (
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var nickname: String = "",
    var password: String = "",
    var confirmPassword: String = ""
)
