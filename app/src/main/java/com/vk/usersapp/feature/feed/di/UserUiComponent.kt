package com.vk.usersapp.feature.feed.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import com.vk.usersapp.core.di.ComponentHolder
import com.vk.usersapp.core.di.DIComponent
import com.vk.usersapp.core.utils.di.CoroutineDispatchersComponentHolder
import com.vk.usersapp.feature.feed.presentation.UserListFeature
import com.vk.usersapp.feature.feed.presentation.UserListReducer

interface UserUiComponent : DIComponent {
    fun userListFeature(): androidx.lifecycle.ViewModelProvider.Factory
    fun userListFeature2(): UserListFeature
}

object UserUiComponentHolder : ComponentHolder<UserUiComponent>() {
    override fun build(): UserUiComponent {
        return object : UserUiComponent {
            override fun userListFeature(): androidx.lifecycle.ViewModelProvider.Factory {
                val dispatchers = CoroutineDispatchersComponentHolder.get().dispatchers
                val reducer = UserListReducer()
                val usersRepositoryImpl = UserRepositoryComponentHolder.get().usersRepository

                return object : androidx.lifecycle.ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(
                        modelClass: Class<T>,
                        extras: CreationExtras
                    ): T {
                        return UserListFeature(
                            dispatchers,
                            reducer,
                            usersRepositoryImpl
                        ) as T
                    }
                }
            }

            override fun userListFeature2(): UserListFeature {
                val dispatchers = CoroutineDispatchersComponentHolder.get().dispatchers
                val reducer = UserListReducer()
                val usersRepositoryImpl = UserRepositoryComponentHolder.get().usersRepository

                return UserListFeature(
                    dispatchers,
                    reducer,
                    usersRepositoryImpl
                )
            }

        }
    }
}