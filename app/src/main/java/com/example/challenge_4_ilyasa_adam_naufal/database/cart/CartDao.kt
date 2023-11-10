package com.example.challenge_4_ilyasa_adam_naufal.database.cart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {

	@Insert
	fun insert(cart: Cart)

	@Update
	fun update(cart: Cart)

	@Query("SELECT * FROM cart_menu")
	fun getAllItems(): LiveData<List<Cart>>

	@Query("DELETE FROM cart_menu")
	fun delete()

	@Query("DELETE FROM cart_menu WHERE id = :itemId")
	fun deleteItemById(itemId: Int)

	@Query("Select * FROM cart_menu WHERE food_name = :foodName")
	fun getCartItemByName(foodName: String): Cart

}
