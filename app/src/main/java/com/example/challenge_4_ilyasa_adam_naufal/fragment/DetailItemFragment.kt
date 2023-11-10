package com.example.challenge_4_ilyasa_adam_naufal.fragment

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.DataListMenu
import com.example.challenge_4_ilyasa_adam_naufal.databinding.FragmentDetailItemBinding
import com.example.challenge_4_ilyasa_adam_naufal.viewModel.SimpleViewModel
import org.koin.android.ext.android.inject

class DetailItemFragment : Fragment() {
	private var _binding: FragmentDetailItemBinding? = null
	private val binding get() = _binding!!

	private val locationUri: String = "https://maps.app.goo.gl/SiFzf18kByYndQqg7"

	private val viewModel: SimpleViewModel by inject()

	@SuppressLint("SetTextI18n")
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentDetailItemBinding.inflate(inflater, container, false)

		viewModel.totalPrice.observe(viewLifecycleOwner) {
			binding.btnadd2.text = "Tambah Ke Keranjang -Rp.$it "

		}

		setData()
		addToCart()
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		try {
			btnBack()
			wViewModel()

			binding.imgloc.setOnClickListener {
				try {
					val intent = Intent(Intent.ACTION_VIEW, Uri.parse(locationUri))
					startActivity(intent)
				} catch (e: ActivityNotFoundException) {
					Toast.makeText(
						requireContext(),
						"Google Maps tidak terinstall.",
						Toast.LENGTH_SHORT
					).show()
				}
			}

		} catch (e: NullPointerException) {
			Toast.makeText(requireContext(), "Error: $e", Toast.LENGTH_SHORT).show()
		}

	}

	private fun btnBack() {
		binding.btnback.setOnClickListener {
			requireActivity().onBackPressed()
		}
	}

	private fun addToCart() {
		binding.btnadd2.setOnClickListener {
			val note = binding.etNote.text.toString()
			viewModel.addToCart(note)
			Toast.makeText(requireContext(), "Success Add Item", Toast.LENGTH_SHORT).show()
		}
	}

	private fun setData() {
		@Suppress("DEPRECATION")
		val data = arguments?.getParcelable<DataListMenu>("dataListMenu")

		Glide.with(binding.imageadd)
			.load(data?.imageUrl)
			.fitCenter()
			.into(binding.imageadd)
		binding.nameadd.text = data?.nama
		binding.description.text = data?.detail
		binding.priceadd.text = data?.hargaFormat
		binding.location.text = data?.alamatResto

		viewModel.initSelectedItem(data!!)


	}

	private fun wViewModel() {
		val observer = Observer<Int> {
			binding.quantity.text = it.toString()
		}

		viewModel.counter.observe(viewLifecycleOwner, observer)

		binding.btnadd1.setOnClickListener {
			viewModel.incrementCount()
		}

		binding.reduce.setOnClickListener {
			viewModel.decrementCount()
		}
	}

}