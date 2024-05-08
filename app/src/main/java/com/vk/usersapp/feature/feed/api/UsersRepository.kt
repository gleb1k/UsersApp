package com.vk.usersapp.feature.feed.api

import com.vk.usersapp.feature.feed.model.User

interface UsersRepository {

    suspend fun getUsers(): List<User>

    suspend fun searchUsers(query: String): List<User>
}