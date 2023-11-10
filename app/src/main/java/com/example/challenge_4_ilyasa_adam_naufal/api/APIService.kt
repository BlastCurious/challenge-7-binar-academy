package com.example.challenge_4_ilyasa_adam_naufal.api

import com.example.challenge_4_ilyasa_adam_naufal.dataClass.CategoryMenu
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.ListMenu
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.OrderRequest
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
	@GET("listmenu")
	suspend fun getListMenu(): ListMenu

	@GET("category-menu")
	suspend fun getCategoryMenu(): CategoryMenu

	@POST("order-menu")
	fun postOrder(@Body orderData: OrderRequest): Call<OrderResponse>
}