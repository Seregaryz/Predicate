package com.example.predicate.data.network

import com.example.predicate.model.mistake.MistakeResponce
import com.example.predicate.model.request_body.ArgumentRequestBody
import com.example.predicate.model.request_body.SignInRequestBody
import com.example.predicate.model.request_body.SignUpRequestBody
import com.example.predicate.model.user.UserAccount
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @POST("/auth/register/")
    fun createAccount(
        @Body signUpRequestBody: SignUpRequestBody
    ): Single<UserAccount>

    @POST("/auth/login/")
    fun signIn(
        @Body signInRequestBody: SignInRequestBody
    ): Single<UserAccount>

    @POST("/api/sentences/")
    fun sendArgument(
        @Body requestBody: ArgumentRequestBody
    ): Single<MistakeResponce>

    @POST("/api/mistakes/{id}/decline")
    fun deleteMistake(
        @Path("id") id: Int?
    ): Completable


}