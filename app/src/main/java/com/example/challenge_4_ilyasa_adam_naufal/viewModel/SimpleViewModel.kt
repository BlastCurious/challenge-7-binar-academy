package com.example.challenge_4_ilyasa_adam_naufal.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.DataListMenu
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.OrderRequest
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.Cart
import com.example.challenge_4_ilyasa_adam_naufal.repository.SimpleRepository
import com.example.challenge_4_ilyasa_adam_naufal.util.Resource
import kotlinx.coroutines.Dispatchers

class SimpleViewModel(private val repository: SimpleRepository) : ViewModel() {

	// CART
	val orderSuccess = repository.orderSuccess
	val counter = repository.counter
	val items = repository.items
	val totalPrice = repository.totalPrice

	fun initSelectedItem(data: DataListMenu) = repository.initSelectedItem(data)
	fun incrementCount() = repository.incrementCount()
	fun decrementCount() = repository.decrementCount()
	fun allCartItems() = repository.getAllCartItems()
	fun deleteitemById(id: Int) = repository.deleteItemCart(id)
	fun deleteAllData() = repository.deleteAllItems()
	fun addToCart(itemnote: String) = repository.addToCart(itemnote)
	fun increment(cart: Cart) = repository.increment(cart)
	fun decrement(cart: Cart) = repository.decrement(cart)
	fun postData(orderRequest: OrderRequest) = repository.postData(orderRequest)


	//API
	fun getAllCategory() = liveData(Dispatchers.IO) {
		try {
			emit(Resource.success(data = repository.getCategory()))
		} catch (e: Exception) {
			emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
		}
	}

	fun getAllMenu() = liveData(Dispatchers.IO) {
		try {
			emit(Resource.success(data = repository.getList()))
		} catch (e: Exception) {
			emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
		}
	}

}