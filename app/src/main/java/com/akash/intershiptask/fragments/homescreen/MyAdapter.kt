package com.akash.intershiptask.fragments.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akash.intershiptask.databinding.RvHomeSingleItemBinding
import com.akash.intershiptask.model.User

class MyAdapter() : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    var userList = ArrayList<User>()

    class ViewHolder(val binding: RvHomeSingleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RvHomeSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvDescription.text = userList[position].description
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(user: ArrayList<User>) {
        this.userList = user
        notifyDataSetChanged()
    }

}