package com.nicelydone.mobiledeveloperinterntest.activities.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicelydone.mobiledeveloperinterntest.connection.response.DataItem
import com.nicelydone.mobiledeveloperinterntest.databinding.UserItemBinding

class UserAdapter(private val onUserSelected: (DataItem) -> Unit): ListAdapter<DataItem, UserAdapter.ViewHolder>(DIFF_CALLBACK) {
   class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
      @SuppressLint("SetTextI18n")
      fun bind(dataItem: DataItem, onUserSelected: (DataItem) -> Unit){
         with(binding){
            userName.text = dataItem.firstName + " " + dataItem.lastName
            userEmail.text = dataItem.email
            Glide.with(itemView.context).load(dataItem.avatar).into(userProfile)

            root.setOnClickListener {
               onUserSelected(dataItem)
            }
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val dataItem = getItem(position)
      if (dataItem != null) {
         holder.bind(dataItem, onUserSelected)
      }
   }

   companion object {
      val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>(){
         override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
         }

         override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
         }
      }
   }
}