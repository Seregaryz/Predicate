package com.example.predicate.data.network.interceptor

import com.example.predicate.system.SessionKeeper
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor(
    private val sessionKeeper: SessionKeeper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = sessionKeeper.token
        request = if (token != null) {
            request.newBuilder().addHeader("Authorization", "Token $token").build()
        } else {
            request.newBuilder().build()
        }
        return chain.proceed(request)
    }
}