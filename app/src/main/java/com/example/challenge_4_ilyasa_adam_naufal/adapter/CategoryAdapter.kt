package com.example.challenge_4_ilyasa_adam_naufal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.DataCategoryMenu
import com.example.challenge_4_ilyasa_adam_naufal.databinding.CategoryItemMenuBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

	//NO MATCHING REQUEST
	private val diffCallBack = object : DiffUtil.ItemCallback<DataCategoryMenu>() {
		override fun areItemsTheSame(
			oldItem: DataCategoryMenu,
			newItem: DataCategoryMenu
		): Boolean = oldItem.id == newItem.id

		override fun areContentsTheSame(
			oldItem: DataCategoryMenu,
			newItem: DataCategoryMenu
		): Boolean = oldItem == newItem
	}

	private val differ = AsyncListDiffer(this, diffCallBack)

	fun submitCategoryMenuResponse(value: List<DataCategoryMenu>) = differ.submitList(value)

	// Membuat Holder
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		return ViewHolder(CategoryItemMenuBinding.inflate(inflater, parent, false))
	}

	override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
		val data = differ.currentList[position]
		data.let { holder.bind(data) }

	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	inner class ViewHolder(
		private var binding:
		CategoryItemMenuBinding
	) : RecyclerView.ViewHolder(binding.root) {
		fun bind(data: DataCategoryMenu) {
			binding.apply {
				titleCat.text = data.nama
				Glide.with(this.categoryImageMenu)
					.load(data.imageUrl)
					.fitCenter()
					.into(binding.categoryImageMenu)
			}
		}
	}
}