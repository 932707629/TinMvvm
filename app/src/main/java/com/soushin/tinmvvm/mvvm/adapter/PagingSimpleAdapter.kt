package com.soushin.tinmvvm.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.ItemCategoryBinding
import com.soushin.tinmvvm.mvvm.repository.entity.CategoryEntity

class PagingSimpleAdapter : PagingDataAdapter<CategoryEntity,RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<CategoryEntity>() {

    override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem == newItem
    }
}){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        var dataBean = getItem(position)
        (holder as DataViewHolder).binding.tvChapterName.text = "Item position $position"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DataViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_category,parent,false))
    }

    inner class DataViewHolder(private val dataBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        var binding = dataBinding
    }

}