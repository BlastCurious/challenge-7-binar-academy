package com.example.challenge_4_ilyasa_adam_naufal.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_4_ilyasa_adam_naufal.R
import com.example.challenge_4_ilyasa_adam_naufal.adapter.CategoryAdapter
import com.example.challenge_4_ilyasa_adam_naufal.adapter.MenuAdapter
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.CategoryMenu
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.DataListMenu
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.ListMenu
import com.example.challenge_4_ilyasa_adam_naufal.databinding.FragmentHomeBinding
import com.example.challenge_4_ilyasa_adam_naufal.util.SharedPreferences
import com.example.challenge_4_ilyasa_adam_naufal.util.Status
import com.example.challenge_4_ilyasa_adam_naufal.viewModel.SimpleViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private var isGrid = true

	private lateinit var firebaseAuth: FirebaseAuth

	private val viewModel: SimpleViewModel by inject()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentHomeBinding.inflate(inflater, container, false)


		firebaseAuth = Firebase.auth
		val currentUser = firebaseAuth.currentUser
		if (currentUser != null) {
			"Hai, ${currentUser.email},".also { binding.greeting.text = it }
		}

		isGrid = SharedPreferences.read("isGrid", true)

		binding.gridlist.setOnClickListener {
			isGrid = !isGrid

			SharedPreferences.write("isGrid", isGrid)
			fetchListMenu()
		}

		fetchCategoryMenu()
		fetchListMenu()

		return binding.root

	}


	@Suppress("KotlinConstantConditions")
	private fun showList(data: ListMenu) {
		if (isGrid) {
			val adapter = MenuAdapter(isGrid, object : MenuAdapter.OnClickListener {
				override fun onClickItem(data: DataListMenu) {
					navigateAndSendDataToDetail(data)
				}
			})

			adapter.submitListMenuResponse(data.data)
			binding.recycleviewVertical.layoutManager = GridLayoutManager(requireActivity(), 2)
			binding.recycleviewVertical.adapter = adapter
			binding.gridlist.setImageDrawable(
				ContextCompat.getDrawable(
					requireActivity(), R.drawable.baseline_view_list_24
				)
			)

		} else {
			val adapter = MenuAdapter(isGrid, object : MenuAdapter.OnClickListener {
				override fun onClickItem(data: DataListMenu) {
					navigateAndSendDataToDetail(data)
				}
			})
			adapter.submitListMenuResponse(data.data)
			binding.recycleviewVertical.layoutManager = LinearLayoutManager(requireActivity())
			binding.recycleviewVertical.adapter = adapter

			binding.gridlist.setImageDrawable(
				ContextCompat.getDrawable(
					requireActivity(), R.drawable.baseline_grid_view_24
				)
			)
		}
	}


	// RecycleViewCategory
	private fun showCategory(data: CategoryMenu?) {
		val adapter = CategoryAdapter()

		adapter.submitCategoryMenu(data?.data ?: emptyList())
		binding.recycleviewHorizontal.layoutManager =
			LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
		binding.recycleviewHorizontal.adapter = adapter
	}

	private fun fetchListMenu() {
		viewModel.getAllMenu().observe(viewLifecycleOwner) {
			when (it.status) {
				Status.SUCCESS -> {
					showList(it.data!!)
					binding.progressBar.visibility = View.GONE
				}

				Status.ERROR -> {
					Log.d("Error", "Error Occured!")
				}

				Status.LOADING -> {
					binding.progressBar.visibility = View.VISIBLE
				}
			}
		}
	}

	private fun fetchCategoryMenu() {
		viewModel.getAllCategory().observe(viewLifecycleOwner) {
			when (it.status) {
				Status.SUCCESS -> {
					showCategory(it.data!!)
				}

				Status.ERROR -> {
					Log.d("Error", "Error Occured!")
				}

				Status.LOADING -> {
					binding.progressBar.visibility = View.VISIBLE
				}
			}
		}
	}

	private fun navigateAndSendDataToDetail(data: DataListMenu) {
		val bundle = bundleOf("dataListMenu" to data)
		Log.d("BUNDLETEST", bundle.toString())
		findNavController().navigate(R.id.action_homeFragment_to_detailItem, bundle)
	}
}