package com.phattarapong.koin.di

import com.phattarapong.koin.BuildConfig
import com.phattarapong.koin.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else {
    OkHttpClient
        .Builder()
        .build()
}

fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)