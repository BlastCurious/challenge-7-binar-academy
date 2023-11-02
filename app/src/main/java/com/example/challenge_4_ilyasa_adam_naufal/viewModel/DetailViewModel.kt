package com.example.challenge_4_ilyasa_adam_naufal.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge_4_ilyasa_adam_naufal.Callback
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.DataListMenu
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.Cart
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.CartDao
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.CartDatabase
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.CartRepo

class DetailViewModel(application: Application) : ViewModel() {

	private val _counter = MutableLiveData(1)
	val counter: LiveData<Int> = _counter

	private val _totalPrice = MutableLiveData<Int?>()
	val totalPrice: MutableLiveData<Int?> = _totalPrice

	private val _selectedItem = MutableLiveData<DataListMenu>()

	private val cartDao: CartDao
	private val repo: CartRepo = CartRepo(application)

	init {
		val db = CartDatabase.getInstance(application)
		cartDao = db.cartDao
	}


	fun initSelectedItem(data: DataListMenu) {
		_selectedItem.value = data
	}

	fun increment() {
		_counter.value = (_counter.value ?: 1) + 1
	}

	fun decrement() {
		val currentValues = _counter.value ?: 1
		if (currentValues > 1) {
			_counter.value = currentValues - 1
		}
	}

	private fun insertItem(cart: Cart) {
		repo.insertData(cart)
	}


	private fun update(cart: Cart) {
		repo.updateQuantityItem(cart)
	}

	fun addToCart(note: String) {
		val selectedItem = _selectedItem.value

		selectedItem?.let {
			val newItem = Cart(
				itemName = it.nama,
				itemNote = note,
				itemPrice = it.harga,
				totalPrice = it.harga!!.times(counter.value!!.toInt()),
				itemQuantity = counter.value!!.toInt(),
				imgId = it.imageUrl
			)

			repo.addOrUpdateCartItem(newItem.itemName, object :Callback {
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






