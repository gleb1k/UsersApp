package com.vk.usersapp.feature.feed.api

import android.util.Log
import com.vk.usersapp.core.Retrofit
import com.vk.usersapp.feature.feed.model.User

class UsersRepository(
    private val api: UsersApi = Retrofit.getClient().create(UsersApi::class.java)
) {

    suspend fun getUsers(): List<User> {
        return api.getUsers(
            skip = 0
        ).users
    }

    suspend fun searchUsers(query: String): List<User> {
        return api.searchUsers(
            query = query,
            skip = 0
        ).users
    }
}