package com.example.predicate.data.network

import com.example.predicate.model.request_body.SignInRequestBody
import com.example.predicate.model.request_body.SignUpRequestBody
import com.example.predicate.model.user.UserAccount
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/auth/register/")
    fun createAccount(
        @Body signUpRequestBody: SignUpRequestBody
    ): Single<UserAccount>

    @POST("/auth/login/")
    fun signIn(
        @Body signInRequestBody: SignInRequestBody
    ): Single<UserAccount>


}