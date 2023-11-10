package com.example.challenge_4_ilyasa_adam_naufal.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_4_ilyasa_adam_naufal.R
import com.example.challenge_4_ilyasa_adam_naufal.adapter.CartAdapter
import com.example.challenge_4_ilyasa_adam_naufal.databinding.FragmentCartBinding
import com.example.challenge_4_ilyasa_adam_naufal.viewModel.SimpleViewModel
import org.koin.android.ext.android.inject


class CartFragment : Fragment() {
	private var _binding: FragmentCartBinding? = null
	private val binding get() = _binding!!
	private lateinit var cartAdapter: CartAdapter
	private val viewModel: SimpleViewModel by inject()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentCartBinding.inflate(inflater, container, false)

		fetchData()

		binding.btnPesan.setOnClickListener {
			findNavController().navigate(R.id.action_cartFragment_to_confirmOrderFragment)
		}

		confirmOrder()

		return binding.root
	}

	private fun fetchData() {
		cartAdapter = CartAdapter(viewModel, false)
		binding.rvCart.setHasFixedSize(true)
		binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
		binding.rvCart.adapter = cartAdapter

		viewModel.allCartItems().observe(viewLifecycleOwner) {
			if (it.isEmpty()) {
				binding.rvCart.visibility = View.GONE
				binding.emptyImage.visibility = View.VISIBLE
				binding.tvEmptyCart.visibility = View.VISIBLE

				binding.resultTotalPrice.text = "0"
			} else {
				cartAdapter.setData(it)

				var totalprice = 0
				it.forEach { item ->
					totalprice += item.totalPrice!!
				}
				val priceText = "Rp. $totalprice"
				binding.resultTotalPrice.text = priceText
			}

		}
	}

	private fun confirmOrder() {
		binding.btnPesan.setOnClickListener {
			if (binding.resultTotalPrice.text.toString() == "Rp. 0") {
				binding.btnPesan.isEnabled = false
				Toast.makeText(requireContext(), "Keranjang kosong", Toast.LENGTH_SHORT).show()
			} else {
				binding.btnPesan.isEnabled = true
				findNavController().navigate(
					R.id.action_cartFragment_to_confirmOrderFragment
				)
			}

		}

	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}