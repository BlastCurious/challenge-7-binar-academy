package com.example.challenge_4_ilyasa_adam_naufal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge_4_ilyasa_adam_naufal.dataClass.DataListMenu
import com.example.challenge_4_ilyasa_adam_naufal.databinding.GridItemMenuBinding
import com.example.challenge_4_ilyasa_adam_naufal.databinding.ListItemMenuBinding

class MenuAdapter(
	var gridType: Boolean = true,
	var onItemClick: OnClickListener
) :
	RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private val diffCallBack = object : DiffUtil.ItemCallback<DataListMenu>() {
		override fun areItemsTheSame(
			oldItem: DataListMenu,
			newItem: DataListMenu
		): Boolean = oldItem.id == newItem.id

		override fun areContentsTheSame(
			oldItem: DataListMenu,
			newItem: DataListMenu
		): Boolean = oldItem == newItem
	}

	private val differ = AsyncListDiffer(this, diffCallBack)

	fun submitListMenuResponse(value: List<DataListMenu>) = differ.submitList(value)

	// Membuat Holder
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		if (gridType) {
			return GridViewHolder(GridItemMenuBinding.inflate(inflater, parent, false))
		} else {
			return ListViewHolder(ListItemMenuBinding.inflate(inflater, parent, false))
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val data = differ.currentList[position]
		when (holder) {
			is GridViewHolder -> holder.bind(data)
			is ListViewHolder -> holder.bind(data)
		}

		holder.itemView.setOnClickListener {
			onItemClick.onClickItem(data)
		}

	}

	inner class GridViewHolder(private var binding: GridItemMenuBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(data: DataListMenu) {
			binding.apply {
				titleMenu.text = data.nama
				priceMenu.text = data.hargaFormat
				Glide.with(this.imageMenu)
					.load(data.imageUrl)
					.fitCenter()
					.into(binding.imageMenu)
			}
		}
	}

	inner class ListViewHolder(private var binding: ListItemMenuBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(data: DataListMenu) {
			binding.apply {
				titleMenu.text = data.nama
				priceMenu.text = data.hargaFormat
				Glide.with(this.imageMenu)
					.load(data.imageUrl)
					.fitCenter()
					.into(binding.imageMenu)
			}
		}
	}

	override fun getItemCount(): Int {
		return differ.currentList.size
	}

	interface OnClickListener {
		fun onClickItem(data: DataListMenu)
	}
}