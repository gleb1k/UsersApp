package com.vk.usersapp.core.utils.di

import com.vk.usersapp.core.di.ComponentHolder
import com.vk.usersapp.core.di.DIComponent
import com.vk.usersapp.core.utils.CoroutineDispatchers
import com.vk.usersapp.core.utils.CoroutineDispatchersImpl

interface CoroutineDispatchersComponent : DIComponent {
    val dispatchers: CoroutineDispatchers
}

object CoroutineDispatchersComponentHolder : ComponentHolder<CoroutineDispatchersComponent>() {
    override fun build(): CoroutineDispatchersComponent {
        return object : CoroutineDispatchersComponent {
            override val dispatchers: CoroutineDispatchers = CoroutineDispatchersImpl()
        }
    }
}