package com.vk.usersapp.feature.feed.di

import com.vk.usersapp.core.di.ComponentHolder
import com.vk.usersapp.core.di.DIComponent
import com.vk.usersapp.core.network.di.NetworkComponentHolder
import com.vk.usersapp.feature.feed.api.UsersApi
import com.vk.usersapp.feature.feed.api.UsersRepository
import com.vk.usersapp.feature.feed.api.UsersRepositoryImpl


interface UserRepositoryComponent : DIComponent {
    val usersRepository: UsersRepository
}

object UserRepositoryComponentHolder : ComponentHolder<UserRepositoryComponent>() {
    override fun build(): UserRepositoryComponent {
        val usersApi: UsersApi = NetworkComponentHolder.get().retrofit.create(UsersApi::class.java)
        val usersRepository = UsersRepositoryImpl(usersApi)

        return object : UserRepositoryComponent {
            override val usersRepository: UsersRepository = usersRepository
        }
    }
}