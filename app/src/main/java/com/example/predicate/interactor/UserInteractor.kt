package com.example.predicate.interactor

import com.example.predicate.data.network.Api
import com.example.predicate.model.request_body.SignInRequestBody
import com.example.predicate.model.request_body.SignUpRequestBody
import com.example.predicate.model.schedulers.SchedulersProvider
import com.example.predicate.model.user.UserAccount
import com.example.predicate.model.user.UserSignUpItem
import com.example.predicate.system.SessionKeeper
import io.reactivex.Single
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val api: Api,
    private val schedulers: SchedulersProvider,
    private val sessionKeeper: SessionKeeper
) {

    fun createAccount (
        userData: UserSignUpItem
    ) : Single<UserAccount> {
        val requestBody = SignUpRequestBody(
            username =  userData.nickname,
            firstName =  userData.firstName,
            lastName =  userData.lastName,
            email =  userData.email,
            password =  userData.password,
            confirm =  userData.confirmPassword
        )
        return api.createAccount(requestBody)
    }

    fun setAccount(userAccount: UserAccount) {
        sessionKeeper.setUserAccount(userAccount)
    }

    fun signIn(login: String, password: String): Single<UserAccount> {
        return api.signIn(SignInRequestBody(login, password))
    }
}