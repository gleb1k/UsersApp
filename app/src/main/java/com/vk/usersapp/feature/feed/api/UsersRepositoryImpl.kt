package com.vk.usersapp.feature.feed.api

import com.vk.usersapp.core.Retrofit
import com.vk.usersapp.feature.feed.model.User

class UsersRepositoryImpl(
    private val api: UsersApi = Retrofit.getClient().create(UsersApi::class.java)
) : UsersRepository {

    override suspend fun getUsers(): List<User> {
        return api.getUsers(
            skip = 0
        ).users
    }

    override suspend fun searchUsers(query: String): List<User> {
        return api.searchUsers(
            query = query,
            skip = 0
        ).users
    }
}