package com.example.challenge_4_ilyasa_adam_naufal.database.cart

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.challenge_4_ilyasa_adam_naufal.Callback
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CartRepo(application: Application) {

	private val _cartDao: CartDao
	private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

	init {
		val db = CartDatabase.getInstance(application)
		_cartDao = db.cartDao
	}

	fun getAllCartItems(): LiveData<List<Cart>> {
		return _cartDao.getAllItems()
	}

	fun deleteItemCart(cartId: Int) {
		executorService.execute {
			_cartDao.deleteItemById(cartId)
		}
	}

	fun deleteAll() {
		executorService.execute { _cartDao.delete() }
	}

	fun updateQuantityItem(cart: Cart) {
		executorService.execute { _cartDao.update(cart) }
	}

	fun insertData(cart: Cart) {
		executorService.execute { _cartDao.insert(cart) }
	}

	fun addOrUpdateCartItem(foodName: String, callback: Callback) {
		executorService.execute {
			val cart = _cartDao.getCartItemByName(foodName)
			callback.onCartLoaded(cart)
		}
	}
}