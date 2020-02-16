package com.soushin.tinmvvm.mvvm.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soushin.tinmvvm.R

class SimpleTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mTv: TextView = itemView.findViewById(R.id.textView) as TextView
}