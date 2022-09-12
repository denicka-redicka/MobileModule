package com.example.projectmaximummodule.di

import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.data.network.interceptors.AddCookieTokenInterceptor
import com.example.projectmaximummodule.data.network.interceptors.GetCookieTokenInterceptor
import java.util.concurrent.TimeUnit
import com.example.projectmaximummodule.data.network.retorfit.LoginApiService
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    private val loginUrl = "https://login.maximumtest.ru/api/v1/"
    private  val baseUrl = "https://education.maximumtest.ru/api/v1/"


    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true

    }

    private val contentType = "application/json".toMediaType()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Login
    fun providesHttpLoginClient(prefs: AppSharedPreferences): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(GetCookieTokenInterceptor())
        .addInterceptor(AddCookieTokenInterceptor(prefs))
        .build()

    @Provides
    fun providesHttpClient(prefs: AppSharedPreferences): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(AddCookieTokenInterceptor(prefs))
        .build()

    @Provides
    fun providesLoginApi(@Login client: OkHttpClient) : LoginApiService  {
        val retrofitLogin = Retrofit.Builder()
            .baseUrl(loginUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
        return retrofitLogin.create()
    }

    @Provides
    @Oauth
    fun providesOauthApi(@Login client: OkHttpClient): MainApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
        return  retrofit.create()
    }

    @Provides
    fun providesMainApi(client: OkHttpClient): MainApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
        return  retrofit.create()
    }

}




