package com.example.challenge_4_ilyasa_adam_naufal.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge_4_ilyasa_adam_naufal.R
import com.example.challenge_4_ilyasa_adam_naufal.SharedPreferences
import com.example.challenge_4_ilyasa_adam_naufal.adapter.CategoryAdapter
import com.example.challenge_4_ilyasa_adam_naufal.adapter.MenuAdapter
import com.example.challenge_4_ilyasa_adam_naufal.api.APIClient
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.CategoryMenu
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.DataListMenu
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.ListMenu
import com.example.challenge_4_ilyasa_adam_naufal.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

	private var _binding: FragmentHomeBinding? = null
	private val binding get() = _binding!!

	private var isGrid = true

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		_binding = FragmentHomeBinding.inflate(inflater, container, false)

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

	@SuppressLint("NotifyDataSetChanged")
	private fun showLayout(data: ListMenu) {
		if (isGrid) {
			showGridLayout(data)
		} else {
			showListLayout(data)
		}

	}

	private fun showListLayout(data: ListMenu) {
		val adapter = MenuAdapter(isGrid, object : MenuAdapter.OnClickListener {
			override fun onClickItem(data: DataListMenu) {
				navigateAndSendDataToDetail(data)
			}
		})
		adapter.submitListMenuResponse(data.data ?: emptyList())
		binding.recycleviewVertical.layoutManager = LinearLayoutManager(requireActivity())
		binding.recycleviewVertical.adapter = adapter

		binding.gridlist.setImageDrawable(
			ContextCompat.getDrawable(
				requireActivity(), R.drawable.baseline_grid_view_24
			)
		)
	}

	private fun showGridLayout(data: ListMenu) {
		val adapter = MenuAdapter(isGrid, object : MenuAdapter.OnClickListener {
			override fun onClickItem(data: DataListMenu) {
				navigateAndSendDataToDetail(data)
			}
		})

		adapter.submitListMenuResponse(data.data ?: emptyList())
		binding.recycleviewVertical.layoutManager = GridLayoutManager(requireActivity(), 2)
		binding.recycleviewVertical.adapter = adapter
		binding.gridlist.setImageDrawable(
			ContextCompat.getDrawable(
				requireActivity(), R.drawable.baseline_view_list_24
			)
		)
	}

	// RecycleViewCategory
	private fun showCategory(data: CategoryMenu) {
		val adapter = CategoryAdapter()

		adapter.submitCategoryMenuResponse(data.data ?: emptyList() )
		binding.recycleviewHorizontal.layoutManager =
			LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
		binding.recycleviewHorizontal.adapter = adapter
	}

	private fun fetchListMenu() {
		APIClient.instance.getListMenu()
			.enqueue(object : Callback<ListMenu> {
				override fun onResponse(
					call: Call<ListMenu>,
					response: Response<ListMenu>
				) {
					val body = response.body()
					val code = response.code()

					if (code == 200) {
						showLayout(body!!)
						binding.progressBar.visibility = View.GONE
					} else {
						binding.progressBar.visibility = View.GONE
					}
				}

				override fun onFailure(call: Call<ListMenu>, t: Throwable) {
					binding.progressBar.visibility = View.GONE
					Toast.makeText(requireActivity(), "Error: $t", Toast.LENGTH_SHORT).show()
				}
			})
	}

	private fun fetchCategoryMenu() {
		APIClient.instance.getCategoryMenu()
			.enqueue(object : Callback<CategoryMenu> {
				override fun onResponse(
					call: Call<CategoryMenu>,
					response: Response<CategoryMenu>
				) {
					val body = response.body()

					if (response.code() == 200) {
						showCategory(body!!)
					}
				}

				override fun onFailure(call: Call<CategoryMenu>, t: Throwable) {
					Toast.makeText(requireActivity(), "Error: $t", Toast.LENGTH_SHORT).show()
				}
			})
	}

	private fun navigateAndSendDataToDetail(data: DataListMenu) {
		val bundle = bundleOf("dataListMenu" to data)
		Log.d("BUNDLETEST", bundle.toString())
		findNavController().navigate(R.id.action_homeFragment_to_detailItem, bundle)
	}
}