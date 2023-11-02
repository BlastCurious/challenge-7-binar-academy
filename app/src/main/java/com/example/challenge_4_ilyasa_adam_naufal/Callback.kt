package com.example.challenge_4_ilyasa_adam_naufal

import com.example.challenge_4_ilyasa_adam_naufal.database.cart.Cart

interface Callback {
	fun onCartLoaded(cart: Cart?): Cart?
}