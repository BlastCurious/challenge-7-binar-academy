package com.example.challenge_4_ilyasa_adam_naufal.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge_4_ilyasa_adam_naufal.database.cart.Cart
import com.example.challenge_4_ilyasa_adam_naufal.databinding.ItemCartBinding
import com.example.challenge_4_ilyasa_adam_naufal.viewModel.SimpleViewModel

class CartAdapter(
	private val viewModel: SimpleViewModel,
	private val cfmOrder: Boolean
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

	private var cartItems: List<Cart> = emptyList()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
		val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return CartViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return cartItems.size
	}

	override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
		val currentItem = cartItems[position]

		holder.bind(currentItem, viewModel = viewModel, cfmOrder)

	}

	class CartViewHolder(private val binding: ItemCartBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(cartItem: Cart, viewModel: SimpleViewModel, cfmOrder: Boolean) {

			if (cfmOrder) {
				binding.delete.visibility = View.GONE
				binding.btnadd.visibility = View.INVISIBLE
				binding.btnreduce.visibility = View.INVISIBLE

				binding.tvDesc.text = cartItem.itemName
				binding.tvPrice.text = cartItem.itemPrice.toString()
				binding.tvNumber.text = cartItem.itemQuantity.toString()
				binding.noteText.text = cartItem.itemNote.toString()

				Glide.with(this.binding.image)
					.load(cartItem.imgId)
					.fitCenter()
					.into(binding.image)
			} else {
				binding.tvDesc.text = cartItem.itemName
				binding.tvPrice.text = cartItem.itemPrice.toString()
				binding.tvNumber.text = cartItem.itemQuantity.toString()
				binding.noteText.text = cartItem.itemNote.toString()

				Glide.with(this.binding.image)
					.load(cartItem.imgId)
					.fitCenter()
					.into(binding.image)
			}
			binding.delete.setOnClickListener {
				viewModel.deleteitemById(cartItem.id)
			}

			binding.btnreduce.setOnClickListener {
				viewModel.decrement(cartItem)
				binding.tvNumber.text = cartItem.itemQuantity.toString()
			}

			binding.btnadd.setOnClickListener {
				viewModel.increment(cartItem)
				binding.tvNumber.text = cartItem.itemQuantity.toString()
			}
		}

	}

	@SuppressLint("NotifyDataSetChanged")
	fun setData(cartItems: List<Cart>) {
		this.cartItems = cartItems
		notifyDataSetChanged()
	}

}