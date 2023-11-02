package com.example.challenge_4_ilyasa_adam_naufal.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_4_ilyasa_adam_naufal.adapter.CartAdapter
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.Order
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.OrderRequest
import com.example.challenge_4_ilyasa_adam_naufal.databinding.FragmentConfirmOrderBinding
import com.example.challenge_4_ilyasa_adam_naufal.viewModel.CartViewModel
import com.example.challenge_4_ilyasa_adam_naufal.viewmodelfactory.ViewModelFactory

class ConfirmOrderFragment : Fragment() {
	private var _binding: FragmentConfirmOrderBinding? = null
	private val binding get() = _binding!!
	private lateinit var cartViewModel: CartViewModel

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		_binding = FragmentConfirmOrderBinding.inflate(inflater, container, false)
		btnBack()
		setUpCartViewModel()
		showRecyclerView()
		summary()
		confirmOrder()
		popupMsg()
		return binding.root
	}

	private fun summary(): Int {
		var grandTotal = 0
		cartViewModel.allCartItems.observe(viewLifecycleOwner) {
			var listMenu = ""
			var priceMenu = ""
			var totalPrice = 0
			it.forEach { item ->
				listMenu += "${item.itemName} - ${item.itemQuantity} x ${item.itemPrice}\n"
				priceMenu += "Rp. ${item.totalPrice}\n"
				totalPrice += item.totalPrice!!
			}

			val totalText = "Rp. $totalPrice"
			grandTotal = totalPrice
			binding.itemName.text = listMenu
			binding.itemQuantity.text = priceMenu
			binding.totalPrice.text = totalText
		}
		return grandTotal
	}

	private fun setUpCartViewModel() {
		val viewModelFactory = ViewModelFactory(requireActivity().application)
		cartViewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
	}

	private fun showRecyclerView() {
		val adapter = CartAdapter(cartViewModel, true)

		binding.rvConfirm.adapter = adapter
		binding.rvConfirm.layoutManager = LinearLayoutManager(requireContext())

		cartViewModel.allCartItems.observe(viewLifecycleOwner) {
			adapter.setData(it)

			var totalPrice = 0
			it.forEach { item ->
				totalPrice += item.totalPrice!!
			}
		}
	}

	private fun btnBack() {
		binding.btnback.setOnClickListener {
			requireActivity().onBackPressed()
		}
	}

	private fun popupMsg() {
		cartViewModel.orderSuccess.observe(viewLifecycleOwner) {
			if (it) {
				Toast.makeText(requireContext(), "Success order", Toast.LENGTH_SHORT).show()
				cartViewModel.deleteAllData()
			}
		}
	}

	private fun confirmOrder() {
		binding.btnOrder.setOnClickListener {
			val username = "Ilyas"
			val orderItem = cartViewModel.allCartItems.value ?: emptyList()

			if (orderItem.isNotEmpty()) {
				//mapping
				val orderRequest = OrderRequest(username, summary(), orderItem.map {
					Order(it.itemName, it.itemQuantity, it.itemNote, it.totalPrice!!)
				})

				cartViewModel.postData(orderRequest)

			} else {
				Toast.makeText(requireContext(), "DataCategoryMenu Kosong", Toast.LENGTH_SHORT).show()
			}

			val dialogPayment = DialogFragment()
			dialogPayment.show(childFragmentManager, "PaymentSuccesfull")
			cartViewModel.deleteAllData()

		}
	}

}