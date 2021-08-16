package com.soushin.tinmvvm.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.soushin.tinmvvm.R
import com.soushin.tinmvvm.databinding.ItemCategoryBinding
import com.soushin.tinmvvm.mvvm.repository.entity.Article

class PagingSimpleAdapter : PagingDataAdapter<Article,RecyclerView.ViewHolder>(DIFF_CALLBACK){

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        /**不调用getItem(position)方法会造成无法继续加载数据**/
        val dataBean = getItem(position)
        val viewHolder=holder as DataViewHolder
        viewHolder.binding.tvPosition.text = "$position"
        viewHolder.binding.acticle=dataBean//.tvChapterName.text = "$position ${dataBean?.title}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DataViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_category,parent,false))
    }

    inner class DataViewHolder(private val dataBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        var binding = dataBinding
    }

    companion object{
        private val DIFF_CALLBACK=object : DiffUtil.ItemCallback<Article>() {

            override fun areItemsTheSame(oldItem: Article, newItem: Article):
                    Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article):
                    Boolean = oldItem == newItem
        }
    }

}
