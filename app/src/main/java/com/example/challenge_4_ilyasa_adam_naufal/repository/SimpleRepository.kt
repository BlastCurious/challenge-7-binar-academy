package com.example.challenge_4_ilyasa_adam_naufal.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.challenge_4_ilyasa_adam_naufal.api.APIClient
import com.example.challenge_4_ilyasa_adam_naufal.api.APIService
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.DataListMenu
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.OrderRequest
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.OrderResponse
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.Cart
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.CartDao
import com.example.challenge_4_ilyasa_adam_naufal.util.Callback
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SimpleRepository(private val cartDao: CartDao) {

	private val apiService: APIService = APIClient.instance
	private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

	// API
	suspend	fun getCategory() = apiService.getCategoryMenu()
	suspend fun getList() = apiService.getListMenu()

	// Cart
	private val _totalPrice = MutableLiveData<Int?>()
	val totalPrice: MutableLiveData<Int?> = _totalPrice
	private val _selectedItem = MutableLiveData<DataListMenu>()
	private val _counter = MutableLiveData(1)
	val counter: LiveData<Int> = _counter
	private val _orderSuccess = MutableLiveData<Boolean>()
	val orderSuccess: LiveData<Boolean> = _orderSuccess
	val items: LiveData<List<Cart>> = getAllCartItems()

	fun initSelectedItem(item: DataListMenu) {
		_selectedItem.value = item
		_totalPrice.value = item.harga

	}

	fun incrementCount() {
		_counter.value = (_counter.value ?: 1) + 1
	}

	fun decrementCount() {
		val currentValue = _counter.value ?: 1
		if (currentValue > 1) {
			_counter.value = currentValue - 1
		}
	}

	fun increment(cart: Cart) {
		val newTotal = cart.itemQuantity + 1
		cart.itemQuantity = newTotal
		cart.totalPrice = cart.itemPrice!!.times(newTotal)

		updateQuantityItem(cart)
	}

	fun decrement(cart: Cart) {
		var newTotal = cart.itemQuantity

		if (newTotal > 1) {
			newTotal = cart.itemQuantity - 1
		}

		cart.itemQuantity = newTotal
		cart.totalPrice = cart.itemPrice!!.times(newTotal)

		updateQuantityItem(cart)
	}

	private fun addOrUpdateCartItem(foodName: String, callback: Callback) {
		executorService.execute {
			val cart = cartDao.getCartItemByName(foodName)
			callback.onCartLoaded(cart)
		}
	}

	private fun insertItem(cart: Cart) {
		insertData(cart)
	}
	private fun insertData(cart: Cart) {
		executorService.execute { cartDao.insert(cart) }
	}

	private fun update(cart: Cart) {
		cartDao.update(cart)
	}

	private fun updateQuantityItem(cart: Cart) {
		executorService.execute { cartDao.update(cart) }
	}

	fun deleteAllItems() {
		executorService.execute { cartDao.delete() }
	}

	fun deleteItemCart(cartId: Int) {
		executorService.execute { cartDao.deleteItemById(cartId) }
	}

	fun getAllCartItems(): LiveData<List<Cart>> = cartDao.getAllItems()
	fun postData(ordersRequest: OrderRequest) {
		val instance = APIClient.instance

		val call = instance.postOrder(ordersRequest)

		call.enqueue(object : retrofit2.Callback<OrderResponse> {
			override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
				if (response.isSuccessful) {
					_orderSuccess.postValue(true)
					deleteAllItems()
				} else {
					_orderSuccess.postValue(false)
				}
			}

			override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
				Log.d("ERROR", "msg: $t")
			}
		})
	}

	fun addToCart(note: String) {
		val selectedItem = _selectedItem.value

		selectedItem?.let {
			val newItem = Cart(
				itemName = it.nama.toString(),
				itemNote = note,
				itemPrice = it.harga,
				totalPrice = it.harga?.times(counter.value!!.toInt()),
				itemQuantity = counter.value!!.toInt(),
				imgId = it.imageUrl.toString()
			)

			addOrUpdateCartItem(newItem.itemName, object : Callback {
				override fun onCartLoaded(cart: Cart?): Cart? {
					if (cart!= null) {
						val total = counter.value!!.toInt() + cart.itemQuantity
						cart.itemQuantity = total
						cart.totalPrice = cart.itemPrice!!.times(total)
						// Memperbarui data dalam database
						update(cart)
					} else {
						insertItem(newItem)
					}
					return cart
				}

			})
		}
	}

}