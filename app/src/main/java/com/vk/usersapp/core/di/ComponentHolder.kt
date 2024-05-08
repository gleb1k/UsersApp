package com.vk.usersapp.core.di

abstract class ComponentHolder<T : DIComponent> {

    private val component: T by lazy { build() }

    abstract fun build(): T

    fun get(): T = component
}