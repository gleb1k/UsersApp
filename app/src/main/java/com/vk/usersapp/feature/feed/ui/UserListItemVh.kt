package com.vk.usersapp.feature.feed.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vk.usersapp.R
import com.vk.usersapp.feature.feed.model.User

class UserListItemVh(view: View) : RecyclerView.ViewHolder(view) {

    private val avatar: ImageView = view.findViewById(R.id.photo)
    private val title: TextView = view.findViewById(R.id.name_and_age)
    private val subtitle: TextView = view.findViewById(R.id.university)

    fun bind(user: User) {
        Glide.with(avatar).load(user.image).into(avatar)
        title.text = "${user.firstName} ${user.lastName}, ${user.age}"
        subtitle.text = user.university
    }
}