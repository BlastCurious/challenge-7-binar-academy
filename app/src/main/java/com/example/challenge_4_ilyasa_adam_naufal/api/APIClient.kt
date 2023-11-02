package com.example.challenge_4_ilyasa_adam_naufal.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
	private const val BASE_URL = "https://testing.jasa-nikah-siri-amanah-profesional.com/"

	private val logging: HttpLoggingInterceptor
		get() {
			val httpLoggingInterceptor = HttpLoggingInterceptor()
			return httpLoggingInterceptor.apply {
				httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
			}
		}

	private val client = OkHttpClient.Builder()
		.addInterceptor(logging)
		.build()

	val instance: APIService by lazy {
		val retrofit = Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()

		retrofit.create(APIService::class.java)
	}
}