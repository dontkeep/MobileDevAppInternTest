package com.nicelydone.mobiledeveloperinterntest.connection

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
   private const val BASE_URL = "https://reqres.in/api/"
   fun getApiService(): ApiService{
      val loggingInterceptor = HttpLoggingInterceptor()
         .setLevel(HttpLoggingInterceptor.Level.BODY)

      val client = OkHttpClient.Builder()
         .addInterceptor(loggingInterceptor)
         .build()

      val retrofit = Retrofit.Builder()
         .baseUrl(BASE_URL)
         .client(client)
         .addConverterFactory(GsonConverterFactory.create())
         .build()

      return retrofit.create(ApiService::class.java)
   }
}