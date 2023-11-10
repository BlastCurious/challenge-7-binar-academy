package com.example.challenge_4_ilyasa_adam_naufal.dataClass

data class Order (
	val name : String,
	val qty : Int,
	val note: String?,
	val price: Int
)
data class OrderRequest(
	val username: String,
	val total: Int,
	val orders: List<Order>


)
