package com.example.predicate.di

import android.content.Context
import android.os.Build
import com.example.predicate.BuildConfig
import com.example.predicate.data.network.Api
import com.example.predicate.data.network.interceptor.AuthHeaderInterceptor
import com.example.predicate.system.SessionKeeper
import com.example.predicate.system.Tls12SocketFactory.Companion.enableTls12
import com.example.predicate.utils.setSslContext
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClientBuilder(
        @ApplicationContext context: Context
    ): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            enableTls12()
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                setSslContext(context.resources)
            }
            val httpLogger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            addNetworkInterceptor(httpLogger)
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        okHttpClientBuilder: OkHttpClient.Builder,
        gson: Gson,
        sessionKeeper: SessionKeeper
    ): OkHttpClient =
        with(okHttpClientBuilder) {
            addNetworkInterceptor(AuthHeaderInterceptor(sessionKeeper))
            val httpLogger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            addInterceptor(httpLogger)
            build()
        }


    @Provides
    @Singleton
    fun provideApi(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Api {
        return with(Retrofit.Builder()) {
            addConverterFactory(GsonConverterFactory.create(gson))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(okHttpClient)
            baseUrl(BuildConfig.BASE_URL)
            build()
        }.create(Api::class.java)
    }

}