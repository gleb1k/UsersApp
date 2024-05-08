package com.vk.usersapp.core.di

import androidx.annotation.MainThread

abstract class LazyComponentHolder<T : DIComponent> : ComponentHolder<T>() {

    private lateinit var lazyComponentCreator: () -> T

    @MainThread
    fun set(component: () -> T) {
        this.lazyComponentCreator = component
    }

    override fun build(): T = lazyComponentCreator()
}