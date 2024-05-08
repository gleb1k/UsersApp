package com.vk.usersapp.core.network.di

import com.vk.usersapp.core.di.DIComponent
import com.vk.usersapp.core.di.LazyComponentHolder
import retrofit2.Retrofit

interface NetworkComponent : DIComponent {
    val retrofit: Retrofit
}

object NetworkComponentHolder : LazyComponentHolder<NetworkComponent>()