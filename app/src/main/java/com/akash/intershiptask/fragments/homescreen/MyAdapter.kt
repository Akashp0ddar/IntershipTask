package com.akash.intershiptask.fragments.homescreen

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akash.intershiptask.databinding.RvHomeSingleItemBinding
import com.akash.intershiptask.model.User
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player


class MyAdapter() : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    var userList = ArrayList<User>()

    class ViewHolder(val binding: RvHomeSingleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            RvHomeSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Log.d("TAG", "onBindViewHolder: ${userList.toString()}")

        if (userList[position].video.isNotEmpty()) {
            Log.d("TAG", "Video: ${userList[position].video }     "+position, )
            holder.binding.exoPlayer.visibility = View.VISIBLE
            val mediaItem: MediaItem =  MediaItem.fromUri(userList[position].video)
            Log.d("TAG", "video: ${mediaItem.mediaMetadata }     "+ position)
            holder.binding.exoPlayer.player?.apply {
                addMediaItem(mediaItem)
                prepare()
                repeatMode = Player.REPEAT_MODE_ALL
                playWhenReady = true

            }
        } else{
            Log.d("TAG", "Image: ${userList[position].imageView!!}     "+position, )

            holder.binding.ivPostImage.visibility = View.VISIBLE
            holder.binding.ivPostImage.setImageBitmap(userList[position].imageView)
        }

        holder.binding.tvDescription.text = userList[position].description
        holder.binding.tvId.text = userList[position].id.toString()
    }



override fun getItemCount(): Int {
    return userList.size
}

fun setData(user: ArrayList<User>) {
    this.userList = user
    notifyDataSetChanged()
}

}